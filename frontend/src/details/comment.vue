<template>
    <article>
        <div>
            <div><a :href="'/browse?author=' + uID">{{ author }}</a> on <v-time :time__="posted"></v-time></div>
            <button @click="toggleStar"><img src="../assets/5.png" alt="Starred" v-if="starred__"><img src="../assets/6.png" alt="Unstarred" v-if="!starred__"><span>{{ stars__ }}</span></button>
        </div>
        <v-description :description="message" line-height="1.5" margin-top="0.5"></v-description>
    </article>
</template>

<script>
    import $ from "jquery";
    import _ from "lodash";
    import VTime from "../components/time";
    import VDescription from "../components/description";

    let messageBox = _.once(() => $("#message-box")[0].__vue__);

    export default {
        name: "v-comment",
        components: {
            VTime,
            VDescription
        },
        props: {
            postID: Number,
            uID: Number,
            author: String,
            posted: Number,
            message: String,
            starred: Boolean,
            stars: Number
        },
        data: function(){return {
            starred__: this.starred,
            stars__: this.stars
        }},
        methods: {
            toggleStar()
            {
                if (!syt.logged)
                {
                    messageBox().message = "You must log in to access this feature";
                    location.replace("#");
                    return;
                }

                $.ajax({
                    method: "POST",
                    url: syt.backend + "/toggle-kudos",
                    xhrFields: {
                        withCredentials: true
                    },
                    data: {
                        post: this.postID
                    },
                    dataType: "text",
                    success: response =>
                    {
                        if (response === "ok")
                        {
                            this.stars__ += this.starred__ ? -1 : 1;
                            this.starred__ = !this.starred__;
                        }
                    },
                });
            }
        }
    }
</script>

<style scoped>
    article
    {
        margin: 1.5rem 0;
    }

    article>div>*
    {
        display: inline-block;
    }

    div>div>*
    {
        margin: 0 0.25rem;
    }

    div>a
    {
        color: unset;
        font-weight: bold;
    }

    button
    {
        color: unset;
        border: unset;
        margin-left: 1rem;
    }

    button:hover
    {
        background-color: lightgray;
    }

    button>img
    {
        position: relative;
        top: 2px;
        margin-right: 0.25rem;
    }

    time
    {
        color: gray;
    }

    div:last-child
    {
        background-color: lightsteelblue;
        border: solid 1px silver;
        border-radius: 2px;
        margin: 0.5rem 0;
        padding: 0.75rem;
    }
</style>