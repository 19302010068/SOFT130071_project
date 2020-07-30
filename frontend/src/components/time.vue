<template>
    <time>{{ time }}</time>
</template>

<script>
    import _ from "lodash";

    let datetime = time =>
    {
        time = _.defaultTo(time, _.now());
        let fragments = new Date(time).toUTCString().match(/^(\w+, \w+ \w+ \w+)( \w+:\w+)/);
        return fragments[1] + "," + fragments[2];
    };

    export default {
        name: "v-time",
        props: {
            time__: Number
        },
        data: function(){return {
            time: datetime(this.time__)
        }}
    }
</script>

<style scoped>

</style>