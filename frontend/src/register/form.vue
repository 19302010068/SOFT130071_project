<template>
    <form-styles>
        <form method="post" @submit.prevent="submit">
            <label>
                <span>Username</span>
                <input type="text" v-model="username" maxlength="15" pattern="[\w.-]{4,15}" required>
            </label>
            <label>
                <span>Email</span>
                <input type="email" v-model="email" pattern="\w+@\w+(\.\w+)*" required>
            </label>
            <label>
                <span>Password</span>
                <input type="password" v-model="pass" maxlength="12" pattern=".{6,12}" required>
            </label>
            <label>
                <span>Confirm password</span>
                <input type="password" v-model="confirm" required>
            </label>
            <div>
                <span></span>
                <input class="action" type="submit" value="Register">
            </div>
        </form>
        <p>
            Usernames must contain between 4 and 15 characters and they may only contain upper/lower case
            alphanumeric characters (A-Z, a-z, 0-9), dot (.), hyphen (-), and underscore (_).
        </p>
        <p>
            Passwords must contain between 6 and 12 characters.
        </p>
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
            username: "",
            email: "",
            pass: "",
            confirm: ""
        }},
        methods: {
            submit()
            {
                if (this.pass !== this.confirm)
                    messageBox().message = "Passwords do not match";
                else if (this.username.substr(0, 12) === this.pass || isWeakPassword(this.pass))
                    messageBox().message = "Your password is too weak";
                else
                {
                    $.ajax({
                        method: "POST",
                        url: syt.backend + "/register",
                        xhrFields: {
                            withCredentials: true
                        },
                        dataType: "text",
                        data: {
                            username: this.username,
                            email: this.email,
                            pass: this.pass
                        },
                        success: response =>
                        {
                            switch (response)
                            {
                                case "ok":
                                    sessionStorage.setItem("messageBox", "Your account has been successfully created");
                                    window.location = "/";
                                    break;
                                case "username":
                                    messageBox().message = "Sorry, but that username has been taken";
                                    break;
                                case "email":
                                    messageBox().message = "Sorry, but there already exists an account for that email";
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }

    function isWeakPassword(password)
    {
        let complete = [/(.)\1{5}/, /^\d*$/, /^password$/i];
        let partial = ["qwertyuiop", "asdfghjkl", "zxcvbnm", "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "abcdefghijklmnopqrstuvwxyz", "012345678901234"];

        if (_.some(complete, _.method("test", password)))
            return true;
        let passwordSlices = [];
        _.forEach(password, (value, index, collection) =>
        {
            passwordSlices.push(new RegExp(collection.substr(index, 6)));
            return index + 6 < collection.length;
        });
        return _.some(partial, value => _.some(passwordSlices, _.method("test", value)));
    }
</script>

<style scoped>

</style>