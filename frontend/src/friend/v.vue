<template>
    <div id="v">
        <v-header location="/friend" @logged="logged"></v-header>
        <div>
            <keep-alive><component :is="active"></component></keep-alive>
            <aside>
                <ul>
                    <li v-for="tab in tabs"><input type="button" :value="tab.name" @click="active = tab.tag"></li>
                </ul>
            </aside>
        </div>
        <v-footer></v-footer>
    </div>
</template>

<script>
    import VHeader from "../components/header";
    import VProfile from "./profile";
    import VPhotos from "./photos";
    import VFavourites from "./favourites";
    import VChats from "./chats";
    import VFooter from "../components/footer";

    export default {
        name: "v",
        components: {
            VHeader,
            VProfile,
            VPhotos,
            VFavourites,
            VChats,
            VFooter
        },
        data: function(){return {
            active: "v-profile",
            tabs: [
                {tag: "v-profile", name: "profile"},
                {tag: "v-photos", name: "photos"},
                {tag: "v-favourites", name: "favourites"},
                {tag: "v-chats", name: "chats"}
            ]
        }},
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
    header+div
    {
        display: flex;
    }

    main
    {
        order: 2;
        width: 100%;
    }

    aside
    {
        order: 1;
        background-color: aliceblue;
        border-right: solid 2px silver;
        padding: 3rem 1rem;
    }

    aside li
    {
        list-style: none;
        margin: 0.75rem 1rem;
    }

    [type="button"]
    {
        font-size: 12pt;
    }
</style>