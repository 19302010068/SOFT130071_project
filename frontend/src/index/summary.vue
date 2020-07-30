<template>
    <article>
        <div class="image"><a :href="'/details?image=' + imageID"><img :class="cls" :src="src" :alt="path" :style="margin" @load="resize($event)"></a></div>
        <div class="author"><a :href="'/browse?author=' + uID">{{ author }}</a></div>
        <div class="contents"><template v-for="(content, index) in contents"><template v-if="index"> | </template><a :href="'/browse?content=' + escape(content)">{{ content }}</a></template></div>
        <v-time :time__="uploaded"></v-time>
    </article>
</template>

<script>
    import _ from "lodash";
    import VTime from "../components/time";

    const SIZE = 200;

    export default {
        name: "v-summary",
        components: {
            VTime
        },
        data: function(){return {
            cls: "",
            margin: ""
        }},
        props: {
            imageID: Number,
            path: String,
            uID: Number,
            author: String,
            content1: String,
            content2: String,
            content3: String,
            content4: String,
            uploaded: Number
        },
        computed: {
            src: function()
            {
                return syt.backend + "/img/" + this.path;
            },
            contents: function()
            {
                return _.compact([this.content1, this.content2, this.content3, this.content4]);
            }
        },
        methods: {
            resize(e)
            {
                let img = e.target;
                if (img.naturalWidth > img.naturalHeight)
                {
                    this.cls = "landscape";
                    let margin = SIZE * (1 - img.naturalWidth / img.naturalHeight) / 2;
                    this.margin = "margin-left: " + margin + "px";
                }
                else
                {
                    this.cls = "portrait";
                    let margin = SIZE * (1 - img.naturalHeight / img.naturalWidth) / 2;
                    this.margin = "margin-top: " + margin + "px";
                }
            },
            escape(s)
            {
                return encodeURIComponent(s);
            }
        }
    }
</script>

<style scoped>
    article
    {
        display: inline-block;
        background-color: aliceblue;
        border: solid 2px silver;
        border-radius: 5px;
        margin: 0.5rem;
    }

    .image
    {
        display: block;
        width: 200px;
        height: 200px;
        overflow: hidden;
        margin: 0.75rem auto;
    }

    a>img
    {
        display: block;
        width: 100%;
        height: 100%;
    }

    .portrait
    {
        height: unset;
    }

    .landscape
    {
        width: unset;
    }

    .image~*
    {
        background-repeat: no-repeat;
        margin: 0.75rem 1rem;
        padding-left: 1.25rem;
    }

    .author
    {
        background-image: url(../assets/7.png);
    }

    .contents
    {
        background-image: url(../assets/40.png);
    }

    time
    {
        display: block;
        background-image: url(../assets/108.png);
    }

    .contents, .contents *
    {
        color: steelblue;
    }

    .author>a
    {
        color: unset;
    }

    time
    {
        color: gray;
    }
</style>