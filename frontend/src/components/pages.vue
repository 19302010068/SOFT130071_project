<template>
    <nav v-if="total > 1">
        <div class="numbers">
            <template v-for="number in numbers">
                <input type="button" v-if="number" :class="{current: number === current}" :value="number" @click="filteredFetch(number)">
                <span v-else>...</span>
            </template>
        </div>
        <div class="operations">
            <input type="button" value="< Prev" @click="filteredFetch(current - 1)">
            <input type="button" value="Next >" @click="filteredFetch(current + 1)">
            <label>
                <span>Go to page:</span>
                <input type="number" v-model.number="toPage" :min="1" :max="total">
                <input type="button" value="Go" @click="filteredFetch(toPage)">
            </label>
        </div>
    </nav>
</template>

<script>
    import $ from "jquery";
    import _ from "lodash";

    const NUM_PER_PAGE = 8;
    const NEARBY_DISTANCE = 2;

    export default {
        name: "v-pages",
        props: {
            photos: Array
        },
        data: function(){return {
            current: undefined,
            toPage: undefined
        }},
        computed: {
            total: function()
            {
                return _.ceil(this.photos.length / NUM_PER_PAGE);
            },
            numbers: function()
            {
                return _(this.current - NEARBY_DISTANCE).range(this.current + NEARBY_DISTANCE + 1).concat(1, this.total).filter(this.inRange).sortBy().sortedUniq().flatMap((value, index, collection) => (index !== 0 && value - collection[index - 1] !== 1) ? [false, value] : value).value();
            }
        },
        mounted: function()
        {
            this.current = 1;
            this.toPage = 1;
            this.fetch(this.current);
        },
        methods: {
            inRange(value)
            {
                return _.inRange(value, 1, this.total + 1);
            },
            fetch(page)
            {
                let success = response =>
                {
                    this.$emit("fetch-photos", response);
                    this.current = page;
                };

                if (this.total === 0)
                    success([]);
                else
                {
                    $.ajax({
                        method: "GET",
                        url: syt.backend + "/photo-overviews",
                        xhrFields: {
                            withCredentials: true
                        },
                        data: {
                            images: _.slice(this.photos, (page - 1) * NUM_PER_PAGE, page * NUM_PER_PAGE)
                        },
                        dataType: "json",
                        success
                    });
                }
            },
            filteredFetch(page)
            {
                if (this.inRange(page) && page !== this.current)
                    this.fetch(page);
            }
        },
        watch: {
            photos()
            {
                this.current = _.clamp(this.current, 1, this.total);
                this.fetch(this.current);
            }
        }
    }
</script>

<style scoped>
    nav
    {
        cursor: default;
        display: block;
        margin-top: 1rem;
    }

    div *
    {
        display: inline;
        margin: 0 0.25rem;
        padding: 0.25rem;
    }

    .current
    {
        font-size: 12pt;
        font-weight: bold;
    }
</style>