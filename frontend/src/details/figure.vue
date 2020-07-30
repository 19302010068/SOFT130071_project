<template>
    <figure @mouseup="hide" @mouseleave="hide">
        <img :src="src" :alt="path" @mousedown.left="show($event)">
        <canvas v-show="showCanvas" :width="canvasWidth" :height="canvasHeight" :style="{top: y + 'px', left: x + 'px'}"></canvas>
    </figure>
</template>

<script>
    import $ from "jquery";

    export default {
        name: "v-figure",
        props: {
            path: String
        },
        data: function(){return {
            width: 32,
            height: 18,
            enlarge: 4,
            canvas: undefined,
            showCanvas: false,
            x: 0,
            y: 0
        }},
        mounted: function()
        {
            this.canvas = $("canvas")[0].getContext("2d");
            this.canvas.fillStyle = "white";
        },
        computed: {
            src: function()
            {
                return syt.backend + "/img/" + this.path;
            },
            canvasWidth: function()
            {
                return this.width * this.enlarge;
            },
            canvasHeight: function()
            {
                return this.height * this.enlarge;
            }
        },
        methods: {
            show(e)
            {
                this.x = e.offsetX - this.canvasWidth / 2;
                this.y = e.offsetY - this.canvasHeight / 2;
                let img = $("figure>img")[0];
                let ratio = img.naturalWidth / img.width;
                this.canvas.fillRect(0, 0, this.canvasWidth, this.canvasHeight);
                this.canvas.drawImage(e.target, (e.offsetX - this.width / 2) * ratio, (e.offsetY - this.height / 2) * ratio, this.width * ratio, this.height * ratio, 0, 0, this.canvasWidth, this.canvasHeight);
                this.showCanvas = true;
            },
            hide()
            {
                this.showCanvas = false;
            }
        }
    }
</script>

<style scoped>
    figure
    {
        position: relative;
        width: fit-content;
    }

    img
    {
        max-width: 100%;
        max-height: 80vh;
    }

    canvas
    {
        position: absolute;
        outline: solid 2px blue;
    }
</style>