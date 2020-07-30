<template>
    <div id="v">
        <v-header location="/account" @logged="logged"></v-header>
        <message-box></message-box>
        <main>
            <h2>Account</h2>
            <template v-if="email">
                <div>
                    <div>
                        <span>Email:</span>
                        <span>{{ email }}</span>
                    </div>
                    <div>
                        <span>Joined:</span>
                        <v-time :time__="joined"></v-time>
                    </div>
                    <div>
                        <span>Last Active:</span>
                        <v-time :time__="lastModified"></v-time>
                    </div>
                    <input class="action" type="button" value="Log out" @click="logOut">
                </div>
                <hr>
                <form method="post" @submit.prevent="change">
                    <dl>
                        <dt><label><input type="checkbox" v-model="hideFavourites"><span>Hide Favourites</span></label></dt>
                        <dd><p>Not allow friends to see your Favourites page.</p></dd>
                    </dl>
                    <input class="action" type="submit" value="Change settings">
                </form>
            </template>
        </main>
        <v-footer></v-footer>
    </div>
</template>

<script>
    import $ from "jquery";
    import _ from "lodash";
    import VHeader from "../components/header";
    import MessageBox from "../components/message-box";
    import VTime from "../components/time";
    import VFooter from "../components/footer";

    let messageBox = _.once(() => $("#message-box")[0].__vue__);

    export default {
        name: "v",
        components: {
            VHeader,
            MessageBox,
            VTime,
            VFooter
        },
        data: function(){return {
            email: undefined,
            joined: undefined,
            lastModified: undefined,
            hideFavourites: undefined
        }},
        mounted: function()
        {
            $.ajax({
                method: "GET",
                url: syt.backend + "/profile",
                xhrFields: {
                    withCredentials: true
                },
                dataType: "json",
                success: response =>
                {
                    if (response !== "unauthorized")
                        _.assign(this, response);
                }
            });
        },
        methods: {
            logged()
            {
                if (!syt.logged)
                    window.location = "/login";
            },
            logOut()
            {
                $.ajax({
                    method: "POST",
                    url: syt.backend + "/log-out",
                    xhrFields: {
                        withCredentials: true
                    },
                    dataType: "text",
                    success: response =>
                    {
                        if (response === "ok")
                            window.location = "/";
                    }
                });
            },
            change()
            {
                $.ajax({
                    method: "POST",
                    url: syt.backend + "/account-settings",
                    xhrFields: {
                        withCredentials: true
                    },
                    data: {
                        hideFavourites: this.hideFavourites
                    },
                    dataType: "text",
                    success: response =>
                    {
                        if (response === "ok")
                            messageBox().message = "Account settings updated"
                    }
                });
            }
        }
    }
</script>

<style scoped>
    span:first-of-type
    {
        font-weight: bold;
    }

    .action
    {
        justify-self: left;
        margin-top: 1rem;
    }

    main>div
    {
        display: grid;
        grid-template-columns: 200px 1fr;
        grid-row-gap: 0.75rem;
    }

    div>div
    {
        display: contents;
    }

    dt
    {
        margin-top: 1rem;
    }

    dd
    {
        margin: 0.25rem 0 0 2.5rem;
    }
</style>