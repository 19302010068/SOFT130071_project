<template>
    <div id="v">
        <v-header location="/friends" @logged="logged"></v-header>
        <message-box></message-box>
        <main>
            <h2>Friends</h2>
            <add-friend></add-friend>
            <hr>
            <friend-list></friend-list>
            <v-requests></v-requests>
        </main>
        <v-footer></v-footer>
    </div>
</template>

<script>
    import VHeader from "../components/header";
    import MessageBox from "../components/message-box";
    import AddFriend from "./add-friend";
    import FriendList from "./friend-list";
    import VRequests from "./requests";
    import VFooter from "../components/footer";

    export default {
        name: "v",
        components: {
            VHeader,
            MessageBox,
            AddFriend,
            FriendList,
            VRequests,
            VFooter
        },
        methods: {
            logged()
            {
                if (!syt.logged)
                    window.location = "/login";
            }
        }
    }
</script>

<style scoped>

</style>