<template>
    <input type="file" @change="change($event)">
</template>

<script>
    export default {
        name: "file-input",
        props: {
            file: File
        },
        methods: {
            change(e)
            {
                this.$emit("update:file", e.target.files[0]);
            }
        }
    }
</script>

<style scoped>
    *
    {
        display: none;
    }
</style>