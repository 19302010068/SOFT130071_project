<template>
    <main>
        <h2>{{ title }}</h2>
        <div v-if="email">
            <div>
                <span>Email:</span>
                <span>{{ email }}</span>
            </div>
            <div>
                <span>Joined:</span>
                <v-time :time__="joined"></v-time>
            </div>
            <div>
                <span>Last Active:</span>
                <v-time :time__="lastModified"></v-time>
            </div>
        </div>
    </main>
</template>

<script>
    import $ from "jquery";
    import VTime from "../components/time";

    export default {
        name: "v-profile",
        components: {
            VTime
        },
        data: function(){return {
            title: undefined,
            email: undefined,
            joined: undefined,
            lastModified: undefined
        }},
        mounted: function()
        {
            let friend = location.toString().match(/.*\?friend=(\d+)#?$/);
            if (friend === null)
            {
                this.error();
                return;
            }

            $.ajax({
                method: "GET",
                url: syt.backend + "/friend-profile",
                xhrFields: {
                    withCredentials: true
                },
                data: {
                    friend: friend[1]
                },
                dataType: "json",
                success: response =>
                {
                    if (response === 404)
                        this.error();
                    else
                    {
                        this.title = response.username;
                        this.email = response.email;
                        this.joined = response.joined;
                        this.lastModified = response.lastModified;
                    }
                }
            });
        },
        methods: {
            error()
            {
                this.title = "404 Not Found";
            }
        },
        watch: {
            title()
            {
                document.title = this.title;
            }
        }
    }
</script>

<style scoped>
    main>div
    {
        display: grid;
        grid-template-columns: 200px 1fr;
        grid-row-gap: 0.75rem;
    }

    div>div
    {
        display: contents;
    }
</style>