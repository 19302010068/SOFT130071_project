<template>
    <div id="v">
        <v-header location="/details"></v-header>
        <message-box></message-box>
        <v-main></v-main>
        <v-footer></v-footer>
    </div>
</template>

<script>
    import VHeader from "../components/header";
    import MessageBox from "../components/message-box";
    import VMain from "./v-main";
    import VFooter from "../components/footer";

    export default {
        name: "v",
        components: {
            VHeader,
            MessageBox,
            VMain,
            VFooter
        }
    }
</script>

<style scoped>

</style>