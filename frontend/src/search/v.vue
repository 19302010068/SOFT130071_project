<template>
    <div id="v">
        <v-header location="/search"></v-header>
        <message-box></message-box>
        <main>
            <h2>Search</h2>
            <v-parameters @load-photos="loadPhotos"></v-parameters>
            <hr>
            <v-result v-if="currentPhotos !== undefined" :photos="currentPhotos">No result found</v-result>
            <v-pages v-if="allPhotos !== undefined" :photos="allPhotos" @fetch-photos="fetchPhotos"></v-pages>
        </main>
        <v-footer></v-footer>
    </div>
</template>

<script>
    import _ from "lodash";
    import VHeader from "../components/header";
    import MessageBox from "../components/message-box";
    import VParameters from "./parameters";
    import VResult from "../components/result";
    import VPages from "../components/pages";
    import VFooter from "../components/footer";

    export default {
        name: "v",
        components: {
            VHeader,
            MessageBox,
            VParameters,
            VResult,
            VPages,
            VFooter
        },
        data: function(){return {
            allPhotos: undefined,
            currentPhotos: undefined
        }},
        methods: {
            loadPhotos(photos)
            {
                this.allPhotos = undefined;
                _.defer(() => this.allPhotos = photos);
            },
            fetchPhotos(photos)
            {
                this.currentPhotos = photos;
            }
        }
    }
</script>

<style scoped>

</style>