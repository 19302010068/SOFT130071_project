<template>
    <dl>
        <template v-for="request in requests">
            <dt>
                <span>{{ request.username }}</span>
                <input type="button" value="Accept" @click="respondTo(request.uID, true)">
                <input type="button" value="Reject" @click="respondTo(request.uID, false)">
            </dt>
            <dd v-if="request.message">{{ request.message }}</dd>
        </template>
    </dl>
</template>

<script>
    import $ from "jquery";
    import _ from "lodash";

    let table = _.once(() => $("table")[0].__vue__);

    export default {
        name: "v-requests",
        data: function(){return{
            requests: undefined
        }},
        mounted: function()
        {
            $.ajax({
                method: "GET",
                url: syt.backend + "/friend-requests",
                xhrFields: {
                    withCredentials: true
                },
                dataType: "json",
                success: response =>
                {
                    this.requests = response;
                }
            });
        },
        methods: {
            respondTo(id, accept)
            {
                $.ajax({
                    method: "POST",
                    url: syt.backend + "/confirm-friend",
                    xhrFields: {
                        withCredentials: true
                    },
                    data: {
                        friend: id,
                        action: accept ? "accept" : "reject"
                    },
                    dataType: "json",
                    success: response =>
                    {
                        if (response === "ok" || response[0] === "ok")
                        {
                            _.pullAllBy(this.requests,[{uID: id}], "uID");
                            this.requests.splice(this.requests.length);
                            if (accept)
                                table().friends = _.concat(table().friends, response[1]);
                        }
                    }
                });
            }
        }
    }
</script>

<style scoped>
    dl
    {
        margin-top: 2rem;
    }

    dt
    {
        font-weight: bold;
        margin-top: 1rem;
    }

    input
    {
        margin-left: 0.75rem;
    }

    dd
    {
        margin: 0.25rem 0 0 2.5rem;
    }
</style>