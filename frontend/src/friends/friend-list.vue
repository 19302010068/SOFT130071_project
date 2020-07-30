<template>
    <table>
        <tr v-if="!empty">
            <th></th>
            <th>Username</th>
            <th>Email</th>
            <th>Joined</th>
        </tr>
        <tr v-for="(friend, index) in friends" @click="visit(friend.uID)">
            <td>{{ index + 1 }}</td>
            <td>{{ friend.username }}</td>
            <td>{{ friend.email }}</td>
            <td><v-time :time__="friend.joined"></v-time></td>
        </tr>
    </table>
</template>

<script>
    import $ from "jquery";
    import _ from "lodash";
    import VTime from "../components/time";

    export default {
        name: "friend-list",
        components: {
            VTime
        },
        data: function(){return {
            friends: undefined,
        }},
        computed: {
            empty: function()
            {
                return _.isEmpty(this.friends);
            }
        },
        mounted: function()
        {
            $.ajax({
                method: "GET",
                url: syt.backend + "/friend-list",
                xhrFields: {
                    withCredentials: true
                },
                dataType: "json",
                success: response =>
                {
                    this.friends = response;
                }
            });
        },
        methods: {
            visit(id)
            {
                window.location = "/friend?friend=" + id;
            }
        }
    }
</script>

<style scoped>
    tr:first-child
    {
        cursor: default;
    }

    tr+tr:hover
    {
        cursor: pointer;
        background-color: lightgray;
    }

    th, td
    {
        padding: 0.5rem 0.5rem;
    }

    td:first-child
    {
        font-size: 10pt;
        font-weight: bold;
        padding-right: 1rem;
    }

</style>