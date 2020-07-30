<template>
    <div id="message-box">
        <p v-if="message" v-html="message" @click="dismiss"></p>
    </div>
</template>

<script>
    import _ from "lodash";

    let timer = _.debounce(function(){this.$data.__message__ = ""}, 5000);

    export default {
        name: "message-box",
        data: function(){return {
            __message__: "",
        }},
        computed: {
            message: {
                get()
                {
                    return this.$data.__message__;
                },
                set(message)
                {
                    this.$data.__message__ = message;
                    this.timer();
                }
            }
        },
        methods: {
            timer,
            dismiss()
            {
                timer.flush();
            }
        },
        mounted: function()
        {
            let msg = sessionStorage.getItem("messageBox");
            if (msg !== null)
            {
                sessionStorage.removeItem("messageBox");
                this.message = msg;
            }
        }
    }
</script>

<style scoped>
    p
    {
        color: orange;
        font-size: 14pt;
        font-weight: bold;
        border: solid 1px gray;
        margin: 1.5rem 4rem;
        padding: 1rem 1.5rem;
    }
</style>