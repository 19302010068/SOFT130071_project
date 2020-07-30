<template>
    <main>
        <h2>{{ title }}</h2>
        <template v-if="photo">
            <v-photo v-bind="photo"></v-photo>
            <template v-if="comments">
                <hr>
                <v-comments :comments="comments"></v-comments>
            </template>
        </template>
    </main>
</template>

<script>
    import _ from "lodash";
    import $ from "jquery";
    import VPhoto from "./photo";
    import VComments from "./comments";

    export default {
        name: "v-main",
        components: {
            VPhoto,
            VComments
        },
        data: function(){return {
            title: "",
            photo: undefined,
            comments: undefined
        }},
        mounted: function()
        {
            let image = location.toString().match(/.*\?image=(\d+)#?$/);
            if (image === null)
            {
                this.error();
                return;
            }

            $.ajax({
                method: "POST",
                url: syt.backend + "/photo-details",
                xhrFields: {
                    withCredentials: true
                },
                data: {
                    image: image[1]
                },
                dataType: "json",
                success: response =>
                {
                    if (response === 404)
                        this.error();
                    else
                    {
                        this.title = response.title;
                        delete response.title;
                        this.photo = response;
                    }
                }
            });

            $.ajax({
                method: "GET",
                url: syt.backend + "/posts",
                xhrFields: {
                    withCredentials: true
                },
                data: {
                    image: image[1]
                },
                dataType: "json",
                success: response =>
                {
                    if (_.isArray(response))
                        this.comments = response;
                }
            });
        },
        methods: {
            error()
            {
                this.title = "404 Not Found";
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
    hr
    {
        margin: 1.5rem 0;
    }
</style>