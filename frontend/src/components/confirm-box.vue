<template>
    <div v-if="show">
        <p><slot></slot></p>
        <input type="button" value="Yes" :class="{action: defaultResult}" @click="dismiss(true)">
        <input type="button" value="No" :class="{action: !defaultResult}" @click="dismiss(false)">
    </div>
</template>

<script>
    export default {
        name: "confirm-box",
        props: {
            show: Boolean,
            result: Boolean,
            defaultResult: Boolean
        },
        methods: {
            dismiss(result)
            {
                this.$emit("update:show", false);
                this.$emit("update:result", result);
            }
        }
    }
</script>

<style scoped>
    div
    {
        text-align: right;
        width: fit-content;
        max-width: 100%;
        border: solid 1px gray;
        padding: 1rem;
    }

    input
    {
        cursor: unset;
    }

    input:first-of-type
    {
        margin: 0.5rem 0.5rem 0 0;
    }
</style>