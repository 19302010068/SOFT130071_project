<template>
    <main>
        <h2>{{ title }}</h2>
        <div class="record">
            <article v-for="chat in chats">
                <v-description :class="{self: chat.self}" :description="chat.message"></v-description>
                <v-time :class="{self: chat.self}" :time__="chat.sent"></v-time>
            </article>
        </div>
        <form method="post" @submit.prevent="send">
            <label><textarea v-model="message" required></textarea></label>
            <div><input type="button" value="Refresh" @click="refresh"><input class="action" type="submit" value="Send"></div>
        </form>
    </main>
</template>

<script>
    import _ from "lodash";
    import $ from "jquery";
    import VDescription from "../components/description";
    import VTime from "../components/time";

    export default {
        name: "v-chats",
        components: {
            VTime,
            VDescription
        },
        data: function(){return {
            title: undefined,
            chats: undefined,
            message: ""
        }},
        mounted: function()
        {
            this.refresh();
        },
        methods: {
            refresh()
            {
                let friend = location.toString().match(/.*\?friend=(\d+)#?$/);
                if (friend === null)
                    return;

                $.ajax({
                    method: "POST",
                    url: syt.backend + "/chat-history",
                    xhrFields: {
                        withCredentials: true
                    },
                    data: {
                        friend: friend[1]
                    },
                    dataType: "json",
                    success: response =>
                    {
                        if (response === 404)
                            this.title = "404 Not Found";
                        else
                        {
                            this.title = "Chat with " + response[0];
                            this.chats = response[1];
                        }
                    }
                });
            },
            send()
            {
                let friend = location.toString().match(/.*\?friend=(\d+)#?$/);
                if (friend === null)
                    return;

                $.ajax({
                    method: "POST",
                    url: syt.backend + "/chat-send",
                    xhrFields: {
                        withCredentials: true
                    },
                    data: {
                        friend: friend[1],
                        message: this.message
                    },
                    dataType: "json",
                    success: response =>
                    {
                        if (_.isNumber(response))
                        {
                            this.chats = _.concat(this.chats, {
                                self: true,
                                message: this.message,
                                sent: response
                            });
                            this.message = "";
                        }
                    }
                });
            }
        }
    }
</script>

<style scoped>
    .record
    {
        border: solid 2px gray;
        max-width: 80vw;
        max-height: 50vh;
        overflow: auto;
    }

    article
    {
        display: grid;
        grid-template-columns: 1fr;
        margin: 1.5rem 0;
    }

    div:first-child
    {
        justify-self: left;
        background-color: lightgray;
        text-align: unset;
        border: solid 1px silver;
        border-radius: 2px;
        margin: 0.5rem 10rem 0.5rem 0.5rem;
        padding: 0.5rem;
    }

    div.self
    {
        justify-self: right;
        background-color: lightsteelblue;
        margin: 0.5rem 0.5rem 0.5rem 10rem;
    }

    time
    {
        justify-self: left;
        margin-left: 0.5rem;
        margin-right: 0.5rem;
        color: gray;
        font-size: 8pt;
    }

    time.self
    {
        justify-self: right;
    }

    textarea
    {
        resize: both;
        width: 100%;
        height: 100px;
        margin-top: 0.5rem;
    }

    div:last-child
    {
        text-align: right;
    }

    [type="submit"]
    {
        margin-top: 0.25rem;
        margin-left: 1rem;
    }
</style>