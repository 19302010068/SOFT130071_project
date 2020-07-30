<template>
    <form method="get" @submit.prevent="search">
        <div class="what">
            <div>Search for</div>
            <div><label><input type="search" v-model="what" required></label></div>
            <input class="action" type="submit" value="Search">
        </div>
        <div class="where">
            <div>Search in</div>
            <div>
                <label><input type="radio" v-model="where" value="title">title</label>
                <label><input type="radio" v-model="where" value="content">content</label>
            </div>
        </div>
        <div class="order">
            <div>Sort by</div>
            <div>
                <label><input type="radio" v-model="order" value="stars">stars</label>
                <label><input type="radio" v-model="order" value="time">time</label>
            </div>
        </div>
    </form>
</template>

<script>
    import _ from "lodash";
    import $ from "jquery";

    export default {
        name: "v-parameters",
        data: function(){return {
            what: "",
            where: "title",
            order: "stars"
        }},
        methods: {
            search()
            {
                $.ajax({
                    method: "GET",
                    url: syt.backend + "/search",
                    xhrFields: {
                        withCredentials: true
                    },
                    data: {
                        [this.where]: this.what,
                        order: this.order
                    },
                    dataType: "json",
                    success: response =>
                    {
                        if (_.isArray(response))
                            this.$emit("load-photos", response);
                    }
                });
            }
        }
    }
</script>

<style scoped>
    form
    {
        margin-bottom: 2rem;
    }

    form>div
    {
        margin: 0.5rem 0;
    }

    div>div
    {
        display: inline-block;
    }

    div>div:first-child
    {
        width: 6rem;
    }

    .action
    {
        margin-left: 0.5rem;
    }
</style>