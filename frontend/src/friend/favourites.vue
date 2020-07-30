<template>
    <main>
        <h2>{{ title }}</h2>
        <p v-if="rejected">They have set up their favourite photos to be invisible to others.</p>
        <template v-else>
            <v-result v-if="currentPhotos !== undefined" :photos="currentPhotos">No photos</v-result>
            <v-pages v-if="allPhotos !== undefined" :photos="allPhotos" @fetch-photos="fetchPhotos"></v-pages>
        </template>
    </main>
</template>

<script>
    import $ from "jquery";
    import VResult from "../components/result";
    import VPages from "../components/pages";

    export default {
        name: "v-favourites",
        components: {
            VResult,
            VPages
        },
        data: function(){return {
            title: undefined,
            rejected: false,
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
                url: syt.backend + "/friend-favourites",
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
                        this.title = response[0] + "'s favourites";
                        if (response[1] === "rejected")
                            this.rejected = true;
                        else
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