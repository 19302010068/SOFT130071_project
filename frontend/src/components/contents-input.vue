<template>
    <div>
        <span>Content</span>
        <span>You can specify up to 4 tags here, in order. At least one is required.</span>
        <label>
            <input type="text" v-model="content1" :pattern="pattern" :required="required">
            <input type="text" v-model="content2">
            <input type="text" v-model="content3">
            <input type="text" v-model="content4">
        </label>
    </div>
</template>

<script>
    import _ from "lodash";

    export default {
        name: "contents-input",
        props: {
            contents: Array
        },
        data: function(){return {
            content1: _.defaultTo(this.contents[0], ""),
            content2: _.defaultTo(this.contents[1], ""),
            content3: _.defaultTo(this.contents[2], ""),
            content4: _.defaultTo(this.contents[3], "")
        }},
        computed: {
            contents__: function()
            {
                return _([this.content1, this.content2, this.content3, this.content4]).map(_.unary(_.trim)).compact().value();
            },
            required: function()
            {
                return _.isEmpty(this.contents__);
            },
            pattern: function()
            {
                return this.required ? "" : ".*";
            }
        },
        watch: {
            contents__()
            {
                this.$emit("update:contents", this.contents__);
            }
        }
    }
</script>

<style scoped>
    div
    {
        display: grid;
        grid-template-columns: inherit;
        grid-row-gap: 0.5rem;
        grid-column: 1/3;
    }

    span:first-child
    {
        font-weight: bold;
    }

    label
    {
        display: flex;
        grid-column: 1/3;
        justify-content: space-between;
    }

    input
    {
        width: 20%;
    }
</style>