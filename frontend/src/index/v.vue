<template>
    <div id="v">
        <v-header location="/"></v-header>
        <message-box></message-box>
        <main>
            <section>
                <h2>Featured photos</h2>
                <v-carousel v-if="featured" :images__="featured" key="1"></v-carousel>
                <v-carousel v-else key="0"></v-carousel>
            </section>
            <hr>
            <section>
                <h2>Latest photos</h2>
                <v-summary v-for="photo in latest" v-bind="photo" :key="photo.imageID"></v-summary>
            </section>
        </main>
        <v-footer></v-footer>
    </div>
</template>

<script>
    import $ from "jquery";
    import VHeader from "../components/header";
    import MessageBox from "../components/message-box";
    import VCarousel from "./carousel";
    import VSummary from "./summary";
    import VFooter from "./footer"

    export default {
        name: "v",
        components: {
            VHeader,
            MessageBox,
            VCarousel,
            VSummary,
            VFooter
        },
        data: function(){return {
            featured: undefined,
            latest: undefined
        }},
        mounted: function()
        {
            $.ajax({
                method: "GET",
                url: syt.backend + "/featured-photos",
                xhrFields: {
                    withCredentials: true
                },
                dataType: "json",
                success: response =>
                {
                    this.featured = response;
                }
            });
            $.ajax({
                method: "GET",
                url: syt.backend + "/latest-photos",
                xhrFields: {
                    withCredentials: true
                },
                dataType: "json",
                success: response =>
                {
                    this.latest = response;
                }
            });
        }
    }
</script>

<style scoped>
    h2
    {
        margin-bottom: 3rem;
    }

    hr
    {
        margin: 2rem 0;
    }
</style>