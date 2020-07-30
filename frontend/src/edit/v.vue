<template>
    <div id="v">
        <v-header location="/edit" @logged="logged"></v-header>
        <message-box></message-box>
        <main>
            <h2>{{ title }}</h2>
            <v-form v-if="photo" :photo="photo"></v-form>
        </main>
        <v-footer></v-footer>
    </div>
</template>

<script>
    import $ from "jquery";
    import VHeader from "../components/header";
    import MessageBox from "../components/message-box";
    import VForm from "./form";
    import VFooter from "../components/footer";

    export default {
        name: "v",
        components: {
            VHeader,
            MessageBox,
            VForm,
            VFooter
        },
        data: function(){return {
            title: "Edit",
            photo: undefined
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
                method: "GET",
                url: syt.backend + "/edit",
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
                        this.photo = response;
                    }
                }
            });
        },
        methods: {
            logged()
            {
                if (!syt.logged)
                    window.location = "/login";
            },
            error()
            {
                this.title = "404 Not Found";
                document.title = this.title;
            }
        }
    }
</script>

<style scoped>

</style>