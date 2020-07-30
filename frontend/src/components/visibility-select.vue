<template>
    <label>
        <span>Visibility</span>
        <select v-model.number="visibility__" required>
            <option :value="undefined">-- Choose an option --</option>
            <option value="1">Public</option>
            <option value="2">Friends</option>
            <option value="3">Private</option>
        </select>
        <span v-if="visibility__ > initial"><span>WARNING: </span>You are attempting to switch to weaker access privileges. The photo's star count may drop if someone previously starred your photo is prevented from viewing it. Comments will not be deleted, though.</span>
    </label>
</template>

<script>
    import _ from "lodash";

    export default {
        name: "visibility-select",
        props: {
            visibility: Number
        },
        data: function(){return {
            initial: _.defaultTo(this.visibility, 3),
            visibility__: this.visibility
        }},
        watch: {
            visibility__(value)
            {
                this.$emit("update:visibility", value);
            }
        }
    }
</script>

<style scoped>
    label
    {
        display: contents;
    }

    span:first-child
    {
        font-weight: bold;
    }

    span:last-child
    {
        grid-column: 1/3;
        color: red;
        line-height: 1.2;
        margin-top: -1.5rem;
    }
</style>