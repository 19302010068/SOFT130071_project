<template>
    <section>
        <v-figure :path="path"></v-figure>
        <hr>
        <section class="info">
            <div class="author">by <a :href="'/browse?author=' + uID">{{ author }}</a> on <v-time :time__="uploaded"></v-time></div>
            <div class="meta">
                <div class="contents">
                    <template v-for="(content, index) in contents"><template v-if="index"> | </template><a :href="'/browse?content=' + escape(content)">{{ content }}</a></template>
                </div>
                <div class="location"><a :href="'/browse?location=' + cityID">{{ cityName }}</a>, <a :href="'/browse?location=' + countryCode">{{ countryName }}</a></div>
            </div>
            <v-description :description="description" line-height="1.5" margin-top="0.5"></v-description>
        </section>
        <button class="action" @click="toggleStar"><img src="../assets/5.png" alt="Starred" v-if="starred__"><img src="../assets/6.png" alt="Unstarred" v-if="!starred__"><span>{{ stars__ }}</span></button>
    </section>
</template>

<script>
    import $ from "jquery";
    import _ from "lodash";
    import VFigure from "./figure";
    import VTime from "../components/time";
    import VDescription from "../components/description";

    let messageBox = _.once(() => $("#message-box")[0].__vue__);

    export default {
        name: "v-photo",
        components: {
            VFigure,
            VTime,
            VDescription
        },
        props: {
            imageID: Number,
            path: String,
            uID: Number,
            author: String,
            uploaded: Number,
            content1: String,
            content2: String,
            content3: String,
            content4: String,
            cityID: Number,
            cityName: String,
            countryCode: String,
            countryName: String,
            description: String,
            starred: Boolean,
            stars: Number
        },
        data: function(){return {
            starred__: this.starred,
            stars__: this.stars
        }},
        computed: {
            contents: function()
            {
                return _.compact([this.content1, this.content2, this.content3, this.content4]);
            }
        },
        methods: {
            escape(s)
            {
                return encodeURIComponent(s);
            },
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
                    url: syt.backend + "/star",
                    xhrFields: {
                        withCredentials: true
                    },
                    data: {
                        image: this.imageID
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
    .info
    {
        background-color: aliceblue;
        border: solid 2px silver;
        border-radius: 5px;
    }

    .info>*
    {
        padding: 0.75rem 0;
        margin: 0 1rem;
    }

    .info>*:not(:last-child)
    {
        border-bottom: solid 1px silver;
    }

    .author>*
    {
        margin: 0 0.25rem;
    }

    .author>a
    {
        color: unset;
        font-weight: bold;
    }

    time
    {
        color: gray;
    }

    .meta *
    {
        color: steelblue;
    }

    .meta>*
    {
        background-repeat: no-repeat;
        padding-left: 1rem;
    }

    .contents
    {
        background-image: url(../assets/40.png);
        margin-bottom: 0.5rem;
    }

    .contents>a
    {
        display: inline;
        margin: 0.25rem;
    }

    .location
    {
        background-image: url(../assets/61.png);
    }

    .location>a:first-child
    {
        margin-left: 0.25rem;
    }

    button
    {
        border-radius: 5px;
        margin-top: 0.5rem;
        padding: 0.5rem 3.5rem;
    }

    button>img
    {
        position: relative;
        top: 2px;
        margin-right: 0.25rem;
    }
</style>