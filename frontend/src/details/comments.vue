<template>
    <section>
        <h2>Comments</h2>
        <v-comment v-for="comment in comments" v-bind="comment" :key="comment.postID"></v-comment>
        <form method="post" @submit.prevent="addComment">
            <h3>Leave a reply</h3>
            <label><textarea v-model="comment" required></textarea></label>
            <input class="action" type="submit" value="Submit comment">
        </form>
    </section>
</template>

<script>
    import $ from "jquery";
    import _ from "lodash";
    import VComment from "./comment";

    let messageBox = _.once(() => $("#message-box")[0].__vue__);

    export default {
        name: "v-comments",
        components: {
            VComment
        },
        props: {
            comments: Array
        },
        data: function(){return {
            comment: ""
        }},
        methods: {
            addComment()
            {
                if (!syt.logged)
                {
                    messageBox().message = "You must log in to access this feature";
                    location.replace("#");
                    return;
                }

                let image = location.toString().match(/.*\?image=(\d+)#?$/);
                if (image === null)
                    return;

                $.ajax({
                    method: "POST",
                    url: syt.backend + "/add-post",
                    xhrFields: {
                        withCredentials: true
                    },
                    data: {
                        image: image[1],
                        comment: this.comment
                    },
                    dataType: "json",
                    success: response =>
                    {
                        this.comment = "";
                        this.comments = _.concat(this.comments, response);
                    }
                });
            }
        }
    }
</script>

<style scoped>
    textarea
    {
        resize: both;
        width: 100%;
        height: 100px;
        margin-top: 0.5rem;
    }

    [type="submit"]
    {
        margin-top: 0.25rem;
    }
</style>