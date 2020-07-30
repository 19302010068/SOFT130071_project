<template>
    <div id="v">
        <v-header location="/favourites" @logged="logged"></v-header>
        <div>
            <main>
                <h2>Favourites</h2>
                <v-result v-if="currentPhotos !== undefined" :photos="currentPhotos">
                    <template #default>No photos yet</template>
                    <template #operations>
                        <img src="../assets/5.png" alt="Unstar" @click="unstar($event)">
                    </template>
                </v-result>
                <v-pages v-if="allPhotos !== undefined" :photos="allPhotos" @fetch-photos="fetchPhotos"></v-pages>
            </main>
            <aside>
                <h2>Footprints</h2>
                <ul v-if="footprints">
                    <li v-for="footprint in footprints"><a :href="'/details?image=' + footprint.imageID">{{ footprint.title }}</a></li>
                </ul>
                <p v-else>No footprints</p>
            </aside>
        </div>
        <v-footer></v-footer>
    </div>
</template>

<script>
    import _ from "lodash";
    import $ from "jquery";
    import VHeader from "../components/header";
    import VResult from "../components/result";
    import VPages from "../components/pages";
    import VFooter from "../components/footer";

    let overview = e => e.target.parentElement.parentElement.__vue__;

    export default {
        name: "v",
        components: {
            VHeader,
            VResult,
            VPages,
            VFooter
        },
        data: function(){return {
            allPhotos: undefined,
            currentPhotos: undefined,
            footprints: undefined
        }},
        mounted: function()
        {
            $.ajax({
                method: "GET",
                url: syt.backend + "/my-favourites",
                xhrFields: {
                    withCredentials: true
                },
                dataType: "json",
                success: response =>
                {
                    if (_.isArray(response))
                        this.allPhotos = response;
                }
            });
            $.ajax({
                method: "GET",
                url: syt.backend + "/footprints",
                xhrFields: {
                    withCredentials: true
                },
                dataType: "json",
                success: response =>
                {
                    if (_.isArray(response))
                        this.footprints = _.reverse(response);
                }
            });
        },
        methods: {
            logged()
            {
                if (!syt.logged)
                    window.location = "/login";
            },
            fetchPhotos(photos)
            {
                this.currentPhotos = photos;
            },
            unstar(e)
            {
                let targetID = overview(e).photo.imageID;
                $.ajax({
                    method: "POST",
                    url: syt.backend + "/star",
                    xhrFields: {
                        withCredentials: true
                    },
                    data: {
                        image: targetID
                    },
                    dataType: "text",
                    success: response =>
                    {
                        if (response === "ok")
                        {
                            _.pull(this.allPhotos, targetID);
                            _.pullAllBy(this.currentPhotos,[{imageID: targetID}], "imageID");
                            this.allPhotos.splice(this.allPhotos.length);
                            this.currentPhotos.splice(this.currentPhotos.length);
                        }
                    }
                });
            }
        }
    }
</script>

<style scoped>
    header+div
    {
        display: flex;
    }

    main
    {
        order: 1;
        width: 100%;
    }

    aside
    {
        order: 2;
        background-color: aliceblue;
        border-left: solid 2px silver;
        min-width: 15rem;
        padding: 1.5rem 1rem;
    }

    aside li
    {
        list-style: disc;
        margin: 0.75rem 1.25rem;
    }
</style>