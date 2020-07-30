<template>
    <section>
        <p v-if="photos.length === 0"><slot></slot></p>
        <v-overview v-else v-for="photo in photos" :photo="photo" :key="photo.imageID"><slot name="operations"></slot></v-overview>
    </section>
</template>

<script>
    import VOverview from "./overview";

    export default {
        name: "v-result",
        components: {
            VOverview
        },
        props: {
            photos: Array
        }
    }
</script>

<style scoped>

</style>