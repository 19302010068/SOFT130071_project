<template>
    <div>
        <label>
            <span>Country/Region</span>
            <select v-model="country__" required>
                <option value="">-- Choose an option --</option>
                <optgroup v-for="(group, name) in countries" :label="name">
                    <option v-for="country in group" :value="country.countryCode">{{ country.countryName }}</option>
                </optgroup>
            </select>
        </label>
        <label v-if="country__">
            <span>City</span>
            <input type="text" v-model="city__" list="city" :pattern="pattern" required>
            <datalist id="city">
                <option v-for="city in cities" :key="city.cityID">{{ city.cityName }}</option>
            </datalist>
        </label>
    </div>
</template>

<script>
    import $ from "jquery";
    import _ from "lodash";

    export default {
        name: "location-select",
        props: {
            country: String,
            city: Number,
            cityName: String
        },
        data: function()
        {
            if (this.country && this.city && this.cityName)
            {
                return {
                    country__: this.country,
                    countries: undefined,
                    city__: this.cityName,
                    cities: [{
                        cityID: this.city,
                        cityName: this.cityName
                    }],
                    pattern: ".*"
                };
            }
            else
            {
                return {
                    country__: "",
                    countries: undefined,
                    city__: "",
                    cities: undefined,
                    pattern: ""
                };
            }
        },
        mounted: function()
        {
            $.ajax({
                method: "GET",
                url: syt.backend + "/countries",
                xhrFields: {
                    withCredentials: true
                },
                dataType: "json",
                success: response =>
                {
                    this.countries = response;
                }
            });
        },
        methods: {
            fetchCities: _.debounce(function()
            {
                $.ajax({
                    method: "GET",
                    url: syt.backend + "/cities",
                    xhrFields: {
                        withCredentials: true
                    },
                    data: {
                        cityName: this.city__,
                        countryCode: this.country__
                    },
                    dataType: "json",
                    success: response =>
                    {
                        this.cities = _.flatMap(response, _(_.sortBy).partialRight("cityName").unary().value());
                    }
                });
            }, 500)
        },
        watch: {
            country__(value)
            {
                this.$emit("update:country", value);
                this.city__ = "";
            },
            city__()
            {
                this.$emit("update:city", 0);
                this.pattern = "";
                this.fetchCities();
            },
            cities()
            {
                let city = _.find(this.cities, {cityName: this.city__});
                if (city !== undefined)
                {
                    this.$emit("update:city", city.cityID);
                    this.pattern = ".*";
                }
            }
        }
    }
</script>

<style scoped>
    div, label
    {
        display: contents;
    }

    span
    {
        font-weight: bold;
    }

    select optgroup
    {
        background-color: gray;
    }

    select option
    {
        background-color: white;
    }

    [type="text"]
    {
        width: unset;
    }
</style>