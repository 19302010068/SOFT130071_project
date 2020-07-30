<template>
    <div>
        <p v-for="(para, index) in paragraphs" :style="index ? other : first">{{ para }}</p>
    </div>
</template>

<script>
    import _ from "lodash";

    export default {
        name: "v-description",
        props: {
            description: String,
            lineHeight: String,
            marginTop: String
        },
        data: function(){return {
            first: {
                lineHeight: this.lineHeight
            },
            other: {
                lineHeight: this.lineHeight,
                marginTop: this.marginTop + "rem"
            }
        }},
        computed: {
            paragraphs: function()
            {
                return _.split(this.description, /\r\n|\r|\n/);
            }
        }
    }
</script>

<style scoped>

</style>