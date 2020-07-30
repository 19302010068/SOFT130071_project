<template>
    <form-styles>
        <form method="post" @submit.prevent="submit">
            <label>
                <template v-if="loginWithUsername">
                    <span>Username</span>
                    <input type="text" v-model="name" required>
                </template>
                <template v-else>
                    <span>Email</span>
                    <input type="email" v-model="name" required>
                </template>
                <input type="button" value="Toggle login type" @click="toggleLoginType">
            </label>
            <label>
                <span>Password</span>
                <input type="password" v-model="pass" required>
            </label>
            <div>
                <span></span>
                <input class="action" type="submit" value="Login">
            </div>
        </form>
        <p>If you don't have an account then please <a href="/register">register</a>.</p>
    </form-styles>
</template>

<script>
    import $ from "jquery";
    import _ from "lodash";
    import FormStyles from "../components/form-styles";

    let messageBox = _.once(() => $("#message-box")[0].__vue__);

    export default {
        name: "v-form",
        components: {
            FormStyles
        },
        data: function(){return {
            loginWithUsername: true,
            name: "",
            pass: "",
        }},
        computed: {
            loginType: function()
            {
                return this.loginWithUsername ? "username" : "email";
            }
        },
        methods: {
            submit()
            {
                $.ajax({
                    method: "POST",
                    url: syt.backend + "/login",
                    xhrFields: {
                        withCredentials: true
                    },
                    data: {
                        [this.loginType]: this.name,
                        pass: this.pass
                    },
                    dataType: "text",
                    success: response =>
                    {
                        switch (response)
                        {
                            case "ok":
                                let loginRedirect = sessionStorage.getItem("loginRedirect");
                                if (loginRedirect)
                                {
                                    sessionStorage.removeItem("loginRedirect");
                                    window.location = loginRedirect;
                                }
                                else
                                    window.location = "/";
                                break;
                            case "unauthorized":
                                messageBox().message = "Wrong " + this.loginType + " or password";
                                break;
                        }
                    }
                });
            },
            toggleLoginType()
            {
                this.loginWithUsername = !this.loginWithUsername;
            }
        }
    }
</script>

<style scoped>
    [type="button"]
    {
        margin-left: 1rem;
    }
</style>