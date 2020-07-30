<template>
    <article>
        <template v-if="photo">
            <div class="image"><a :href="href"><img :class="cls" :src="src" :alt="photo.path" :style="margin" @load="resize($event)"></a></div>
            <div class="title"><h3>{{ photo.title }}</h3><slot></slot></div>
            <v-description class="description" :description="photo.description" line-height="1.2" margin-top="0"></v-description>
        </template>
        <div class="title" v-else><h3>404 Not Found</h3></div>
    </article>
</template>

<script>
    import VDescription from "./description";

    const SIZE = 200;

    export default {
        name: "v-overview",
        components: {
            VDescription
        },
        props: {
            photo: Object
        },
        data: function(){return {
            cls: "",
            margin: ""
        }},
        computed: {
            href: function()
            {
                return "/details?image=" + this.photo.imageID;
            },
            src: function()
            {
                return syt.backend + "/img/" + this.photo.path;
            },
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
            }
        }
    }
</script>

<style scoped>
    article
    {
        display: grid;
        grid-template-columns: 200px 1fr;
        grid-template-rows: 80px 120px;
    }

    .image
    {
        display: inline-block;
        grid-column: 1;
        grid-row: 1/2;
        width: 200px;
        height: 200px;
        overflow: hidden;
    }

    a>img
    {
        display: block;
        width: 100%;
        height: 100%;
        margin: auto;
    }

    .portrait
    {
        height: unset;
    }

    .landscape
    {
        width: unset;
    }

    .title
    {
        position: relative;
        grid-column: 2;
        grid-row: 1;
        margin: auto 2rem;
    }

    .title>*
    {
        display: inline-block;
    }

    h3
    {
        margin-right: 1rem;
    }

    h3~*
    {
        cursor: pointer;
        margin-left: 0.75rem;
    }

    .description
    {
        grid-column: 2;
        grid-row: 2;
        height: 3.6rem;
        overflow: hidden;
        text-overflow: ellipsis;
        margin: auto 2rem;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 3;
    }

    article
    {
        border-bottom: solid 1px silver;
    }

    article:nth-of-type(odd)
    {
        background-color: lightgray;
    }

    article:nth-of-type(even)
    {
        background-color: aliceblue;
    }
</style>