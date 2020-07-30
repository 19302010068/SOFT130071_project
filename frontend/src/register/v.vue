<template>
    <div id="v">
        <v-header location="/register" @logged="logged"></v-header>
        <message-box></message-box>
        <main>
            <h2>Register</h2>
            <v-form></v-form>
        </main>
        <v-footer></v-footer>
    </div>
</template>

<script>
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
        methods: {
            logged()
            {
                if (syt.logged)
                    window.location = "/";
            }
        }
    }
</script>

<style scoped>

</style>