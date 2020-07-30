<template>
    <div id="v">
        <v-header location="/browse"></v-header>
        <main>
            <h2>{{ title }}</h2>
            <v-result v-if="currentPhotos !== undefined" :photos="currentPhotos">No photos</v-result>
            <v-pages v-if="allPhotos !== undefined" :photos="allPhotos" @fetch-photos="fetchPhotos"></v-pages>
        </main>
        <v-footer></v-footer>
    </div>
</template>

<script>
    import $ from "jquery";
    import _ from "lodash";
    import VHeader from "../components/header";
    import VResult from "../components/result";
    import VPages from "../components/pages";
    import VFooter from "../components/footer";

    export default {
        name: "v",
        components: {
            VPages,
            VResult,
            VFooter,
            VHeader
        },
        data: function(){return {
            title: undefined,
            allPhotos: undefined,
            currentPhotos: undefined
        }},
        mounted: function()
        {
            let author = location.toString().match(/.*\?(author)=(\d+)#?$/);
            let content = location.toString().match(/.*\?(content)=(.+)#?$/);
            let city = location.toString().match(/.*\?(location)=(\d+)#?$/);
            let country = location.toString().match(/.*\?(location)=([A-Z]{2})#?$/);

            let data;
            if (author !== null)
                data = author;
            else if (content !== null)
                data = content;
            else if (city !== null)
                data = city;
            else if (country !== null)
                data = country;
            else
            {
                this.error();
                return;
            }

            $.ajax({
                method: "GET",
                url: syt.backend + "/browse",
                xhrFields: {
                    withCredentials: true
                },
                data: {
                    [data[1]]: data[2]
                },
                dataType: "json",
                success: response =>
                {
                    if (response === 404)
                        this.error();
                    else
                    {
                        this.title = _.upperFirst(data[1]) + ": " + response[0];
                        this.allPhotos = response[1];
                    }
                }
            });
        },
        methods: {
            error()
            {
                this.title = "404 Not Found";
            },
            fetchPhotos(photos)
            {
                this.currentPhotos = photos;
            }
        },
        watch: {
            title()
            {
                document.title = this.title;
            }
        }
    }
</script>

<style scoped>

</style>