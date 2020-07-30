<template>
    <div class="carousel" @mouseover="hover = true" @mouseleave="hover = false">
        <input type="button" value="<" v-show="hover" @click="left">
        <transition-group :name="(direction > 0) ? 'right' : 'left'">
            <div class="image" v-for="(image, index) in images" v-show="index === active" :key="image.id"><a :href="image.href"><img :class="image.cls" :src="image.src" :alt="image.alt" @load="resize($event)"></a></div>
        </transition-group>
        <input type="button" value=">" v-show="hover" @click="right">
    </div>
</template>

<script>
    import _ from "lodash";

    let toImageObjects = images => _.map(images, image =>
    {
        return {
            id: image.imageID,
            href: "/details?image=" + image.imageID,
            src: syt.backend + "/img/" + image.path,
            alt: image.path,
            cls: "",
        };
    });
    let slide = _.debounce(function()
    {
        this.active += this.direction;
        if (!(this.active in this.images))
            this.active -= this.direction * this.images.length;
        this.slide();
    }, 3000);

    export default {
        name: "v-carousel",
        props: {
            images__: Array
        },
        data: function(){return {
            images: toImageObjects(this.images__),
            hover: false,
            active: 0,
            direction: 1,
        }},
        mounted: function()
        {
            this.slide();
        },
        methods: {
            resize(e)
            {
                let img = e.target;
                let cls = (img.naturalWidth / img.naturalHeight > 16 / 9) ? "landscape" : "portrait";
                _.find(this.images, value => value.alt === img.alt).cls = cls;
            },
            slide,
            left: _.throttle(function()
            {
                this.direction = -1;
                slide.flush();
            }, 500),
            right: _.throttle(function()
            {
                this.direction = 1;
                slide.flush();
            }, 500)
        }
    }
</script>

<style scoped>
    .carousel
    {
        position: relative;
        background-image: linear-gradient(to right, transparent, lightgray, transparent);
        overflow: hidden;
        width: 640px;
        height: 360px;
        margin: 0 auto;
    }

    [type="button"]
    {
        z-index: 1;
        position: absolute;
        top: 0;
        color: silver;
        font-size: 24pt;
        width: 100px;
        height: 100%;
    }

    [type="button"]:first-of-type
    {
        left: 0;
    }

    [type="button"]:last-of-type
    {
        right: 0;
    }

    [type="button"]:hover
    {
        background-color: rgba(255,255,255,0.25);
        text-decoration: unset;
    }

    [type="button"]:active
    {
        background-color: rgba(192,192,192,0.25);
        text-decoration: unset;
    }

    .image
    {
        position: absolute;
        width: 100%;
        height: 100%;
    }

    a
    {
        display: flex;
        width: 100%;
        height: 100%;
    }

    img
    {
        display: block;
        width: 100%;
        height: 100%;
        margin: auto;
    }

    .portrait
    {
        width: unset;
    }

    .landscape
    {
        height: unset;
    }

    .left-leave-to, .right-enter
    {
        margin-left: 640px;
    }

    .left-enter-active, .left-leave-active, .right-enter-active, .right-leave-active
    {
        transition: all 750ms ease;
    }

    .left-enter-to, .right-enter-to
    {
        margin-left: 0;
    }

    .left-enter, .right-leave-to
    {
        margin-left: -640px;
    }
</style>