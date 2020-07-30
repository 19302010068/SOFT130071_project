<template>
    <div id="v">
        <v-header location="/photos" @logged="logged"></v-header>
        <main>
            <h2>My photos</h2>
            <confirm-box :show.sync="showConfirm" :result.sync="confirmResult" :default-result="false" style="display: inline-block; position: absolute; top: 0.25rem; right: 0; margin-top: -1rem;">Are you sure you want to delete the photo? This operation is NOT reversible.</confirm-box>
            <v-result v-if="currentPhotos !== undefined" :photos="currentPhotos">
                <template #default>No photos yet</template>
                <template #operations>
                    <img src="../assets/60.png" alt="Edit" @click="edit($event)">
                    <img src="../assets/19.png" alt="Delete" @click="del($event)">
                </template>
            </v-result>
            <v-pages v-if="allPhotos !== undefined" :photos="allPhotos" @fetch-photos="fetchPhotos"></v-pages>
        </main>
        <v-footer></v-footer>
    </div>
</template>

<script>
    import _ from "lodash";
    import $ from "jquery";
    import VHeader from "../components/header";
    import ConfirmBox from "../components/confirm-box";
    import VResult from "../components/result";
    import VPages from "../components/pages";
    import VFooter from "../components/footer";

    let box = _.memoize(() => $("h2+div"));
    let overview = e => e.target.parentElement.parentElement.__vue__;
    let targetID;

    export default {
        name: "v",
        components: {
            VHeader,
            ConfirmBox,
            VResult,
            VPages,
            VFooter
        },
        data: function(){return {
            allPhotos: undefined,
            currentPhotos: undefined,
            showConfirm: false,
            confirmResult: undefined
        }},
        mounted: function()
        {
            $.ajax({
                method: "GET",
                url: syt.backend + "/my-photos",
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
            edit(e)
            {
                window.location = "/edit?image=" + overview(e).photo.imageID;
            },
            del(e)
            {
                this.showConfirm = true;
                this.confirmResult = undefined;
                targetID = overview(e).photo.imageID;
                _.defer(() => box().insertAfter($(e.target)));
            }
        },
        watch: {
            confirmResult()
            {
                if (this.confirmResult !== undefined)
                {
                    box().insertAfter($("h2"));
                    box.cache.clear();
                    if (this.confirmResult)
                    {
                        $.ajax({
                            method: "POST",
                            url: syt.backend + "/delete-photo",
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
                                    this.currentPhotos.splice(this.currentPhotos.length);
                                }
                            }
                        });
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>