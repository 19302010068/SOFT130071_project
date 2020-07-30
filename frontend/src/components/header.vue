<template>
    <header>
        <div class="info">
            <div v-if="logged">Logged in as <strong>{{ logged }}</strong></div>
            <div><v-time></v-time></div>
        </div>
        <h1>Share Your Travels</h1>
        <nav>
            <ul class="site">
                <li v-for="item in site"><a :class="{strong: location === item.href}" :href="item.href">{{ item.title }}</a></li>
                <li v-if="!logged"><a :class="{strong: location === '/login'}" href="/login" @click="setLoginRedirect">Login</a></li>
            </ul>
            <div class="user" v-if="logged">
                <span>Me</span>
                <ul>
                    <li v-for="item in user"><a :href="item.href">{{ item.title }}</a></li>
                </ul>
            </div>
        </nav>
    </header>
</template>

<script>
    import $ from "jquery";
    import VTime from "./time";

    export default {
        name: "v-header",
        components: {
            VTime
        },
        props: {
            location: String
        },
        data: function(){return {
            logged: undefined,
            site: [
                {href: "/", title: "Home"},
                {href: "/search", title: "Search"}
            ],
            user: [
                {href: "/favourites", title: "Favourites"},
                {href: "/upload", title: "Upload"},
                {href: "/photos", title: "My photos"},
                {href: "/friends", title: "Friends"},
                {href: "/account", title: "Account"}
            ]
        }},
        mounted: function()
        {
            $.ajax({
                method: "GET",
                url: syt.backend + "/logged",
                xhrFields: {
                    withCredentials: true
                },
                dataType: "json",
                success: response =>
                {
                    if (response === "no")
                    {
                        this.logged = false;
                        syt.logged = this.logged;
                    }
                    else if (response[0] === "yes")
                    {
                        this.logged = response[1];
                        syt.logged = this.logged;
                    }
                    this.$emit("logged");
                }
            });
        },
        methods: {
            setLoginRedirect()
            {
                sessionStorage.setItem("loginRedirect", location.toString().match(/.*(\/.*)/)[1]);
            }
        }
    }

    window.syt = {
        backend: "http://localhost:8081"
    };
</script>

<style scoped>
    *, a[href]
    {
        color: white;
        text-decoration: none;
        border-radius: unset;
    }

    .info
    {
        background-color: #8CADE4;
        padding: 0.25rem;
    }

    .info>*
    {
        text-align: right;
        margin: 0.25rem;
    }

    h1
    {
        background-image: url(../assets/104.png);
        font-size: 24pt;
        padding: 1rem 0 1rem 4rem;
    }

    nav
    {
        background-image: url(../assets/825.png);
        border: 4px #213D45;
        border-style: solid none solid none;
        padding-left: 3.25rem;
    }

    nav>*
    {
        display: inline-block;
    }

    .site li
    {
        list-style: none;
        display: inline-block;
        font-size: 14pt;
        padding: 0.5rem;
    }

    .site li>*
    {
        padding: 0.25rem;
    }

    .site a:hover
    {
        background-color: orangered;
    }

    .site a:active
    {
        background-color: rgba(255,69,0,0.75);
    }

    .user
    {
        position: relative;
        z-index: 1;
    }

    .user>span
    {
        cursor: default;
        display: inline-block;
        font-size: 14pt;
        padding: 0.5rem 1rem;
    }

    .user>span:hover
    {
        text-decoration: underline;
    }

    .user>ul
    {
        display:none;
        position: absolute;
        top: 0;
        left: 3.25rem;
        background-color: slategray;
        text-align: left;
        border: solid 1px lightslategray;
        border-radius: 5px;
        margin-right: 5px;
    }

    .user>span:hover+ul, .user>ul:hover
    {
        display: block;
    }

    .user li
    {
        list-style: none;
    }

    .user li+li
    {
        border-top: solid 1px silver;
    }

    .user a
    {
        display: block;
        width: 5.5rem;
        background: no-repeat 3px 4px;
        padding: 0.25rem 0.25rem 0.25rem 1.25rem;
    }

    .user a:hover
    {
        background-color: #8395A8;
    }

    .user a[href="/upload"]
    {
        background-image: url(../assets/132.png);
    }

    .user a[href="/photos"]
    {
        background-image: url(../assets/59.png);
    }

    .user a[href="/favourites"]
    {
        background-image: url(../assets/5.png);
    }

    .user a[href="/friends"]
    {
        background-image: url(../assets/259.png);
    }

    .user a[href="/account"]
    {
        background-image: url(../assets/7.png);
    }

    .strong
    {
        font-weight: bold;
    }
</style>