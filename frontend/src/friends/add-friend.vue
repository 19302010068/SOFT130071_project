<template>
    <section>
        <form method="post" @submit.prevent="addFriend">
            <label>
                <span>Username</span>
                <input type="text" v-model="username" placeholder="Enter a friend's username in full" required>
            </label>
            <label>
                <span>Message</span>
                <input type="text" v-model="message" placeholder="Add a message">
            </label>
            <input class="action" type="submit" value="Add friend">
        </form>
        <div>
            <p>This will send a friend request to the user. If they accept your request then you are added to their list and they will be added to your list.</p>
            <p>You will see friend requests at the bottom of this web page (if there is any).</p>
        </div>
    </section>
</template>

<script>
    import $ from "jquery";
    import _ from "lodash";

    let messageBox = _.once(() => $("#message-box")[0].__vue__);

    export default {
        name: "add-friend",
        data: function(){return {
            username: "",
            message: ""
        }},
        methods: {
            addFriend()
            {
                $.ajax({
                    method: "POST",
                    url: syt.backend + "/add-friend",
                    xhrFields: {
                        withCredentials: true
                    },
                    data: {
                        username: this.username,
                        message: this.message ? this.message : undefined
                    },
                    dataType: "text",
                    success: response =>
                    {
                        if (response !== "unauthorized")
                        {
                            this.username = "";
                            this.message = "";
                        }
                        switch (response)
                        {
                            case "ok":
                                messageBox().message = "Your request has been successfully sent";
                                break;
                            case "unauthorized":
                                messageBox().message = "The username you entered does not exist";
                                break;
                            case "already":
                                messageBox().message = "They are already your friend";
                                break;
                            case "self":
                                messageBox().message = "You can view your own info on Account page :)";
                                break;
                        }
                    }
                });
            }
        }
    }
</script>

<style scoped>
    form
    {
        display: grid;
        grid-template-columns: 100px 300px 1fr;
        grid-row-gap: 0.75rem;
        margin-top: 2rem;
    }

    label
    {
        display: contents;
    }

    span
    {
        grid-column: 1;
    }

    [type="text"]
    {
        grid-column: 2;
        width: unset;
    }

    [type="submit"]
    {
        grid-row: 1;
        grid-column: 3;
        justify-self: left;
        margin-left: 0.5rem;
    }

    p:first-of-type
    {
        margin-top: 1.5rem;
    }

    p
    {
        line-height: 1.5;
    }
</style>