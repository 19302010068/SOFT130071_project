<template>
    <main>
        <h2>{{ title }}</h2>
        <v-result v-if="currentPhotos !== undefined" :photos="currentPhotos">No photos</v-result>
        <v-pages v-if="allPhotos !== undefined" :photos="allPhotos" @fetch-photos="fetchPhotos"></v-pages>
    </main>
</template>

<script>
    import $ from "jquery";
    import VResult from "../components/result";
    import VPages from "../components/pages";

    export default {
        name: "v-photos",
        components: {
            VResult,
            VPages
        },
        data: function(){return {
            title: undefined,
            allPhotos: undefined,
            currentPhotos: undefined
        }},
        mounted: function()
        {
            let friend = location.toString().match(/.*\?friend=(\d+)#?$/);
            if (friend === null)
                return;

            $.ajax({
                method: "GET",
                url: syt.backend + "/friend-photos",
                xhrFields: {
                    withCredentials: true
                },
                data: {
                    friend: friend[1]
                },
                dataType: "json",
                success: response =>
                {
                    if (response === 404)
                        this.title = "404 Not Found";
                    else
                    {
                        this.title = response[0] + "'s photos";
                        this.allPhotos = response[1];
                    }
                }
            });
        },
        methods: {
            fetchPhotos(photos)
            {
                this.currentPhotos = photos;
            }
        }
    }
</script>

<style scoped>

</style>