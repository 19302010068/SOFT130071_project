<template>
    <form method="post" @submit.prevent="submit">
        <div>
            <file-input accept="image/*" :file.sync="file"></file-input>
            <input class="action" type="button" value="Choose a photo" @click="chooseFile">
            <span>{{ alt }}</span>
        </div>
        <div v-if="file !== undefined">
            <img :src="src" :alt="alt">
            <hr>
            <div class="info">
                <label>
                    <span>Title</span>
                    <input type="text" v-model="title" required>
                </label>
                <label>
                    <span>Description</span>
                    <textarea v-model="description" required></textarea>
                </label>
                <contents-input :contents.sync="contents"></contents-input>
                <visibility-select :visibility.sync="visibility"></visibility-select>
                <location-select :country.sync="country" :city.sync="city" :city-name="cityName"></location-select>
                <confirm-box :show.sync="showConfirm" :result.sync="confirmResult" default-result>Commit changes?</confirm-box>
                <input v-if="!showConfirm" class="action" type="submit" value="Edit">
            </div>
        </div>
    </form>
</template>

<script>
    import $ from "jquery";
    import _ from "lodash";
    import FileInput from "../components/file-input";
    import ContentsInput from "../components/contents-input";
    import VisibilitySelect from "../components/visibility-select";
    import LocationSelect from "../components/location-select";
    import ConfirmBox from "../components/confirm-box";

    let messageBox = _.once(() => $("#message-box")[0].__vue__);

    export default {
        name: "v-form",
        components: {
            FileInput,
            ContentsInput,
            VisibilitySelect,
            LocationSelect,
            ConfirmBox
        },
        props: {
            photo: Object
        },
        data: function(){return {
            file: null,
            src: syt.backend + "/img/" + this.photo.path,
            alt: this.photo.path,
            title: this.photo.title,
            description: this.photo.description,
            contents: [this.photo.content1, this.photo.content2, this.photo.content3, this.photo.content4],
            visibility: this.photo.visibilityID,
            country: this.photo.countryCode,
            city: this.photo.cityID,
            cityName: this.photo.cityName,
            showConfirm: false,
            confirmResult: undefined
        }},
        methods: {
            submit()
            {
                this.showConfirm = true;
            },
            chooseFile(e)
            {
                $(e.target).prev().click();
            }
        },
        watch: {
            file()
            {
                if (this.file === undefined)
                    this.alt = undefined;
                else
                {
                    let reader = new FileReader();
                    reader.readAsDataURL(this.file);
                    reader.onload = () =>
                    {
                        this.src = reader.result;
                        this.alt = this.file.name;
                    }
                }
            },
            confirmResult()
            {
                let error = () =>
                {
                    this.confirmResult = false;
                    messageBox().message = "Unauthorized operation. You may try uploading again or refreshing the page";
                    location.replace("#");
                };

                if (this.confirmResult === true)
                {
                    let data = {
                        image: this.photo.imageID,
                        title: this.title,
                        description: this.description,
                        contents: this.contents,
                        visibility: this.visibility,
                        city: this.city
                    };
                    if (this.file !== null)
                        data.file = this.file.name;

                    $.ajax({
                        method: "POST",
                        url: syt.backend + "/edit",
                        xhrFields: {
                            withCredentials: true
                        },
                        data,
                        dataType: "text",
                        success: response =>
                        {
                            switch (response)
                            {
                                case "ok":
                                    if (this.file === null)
                                        window.location = "/photos";
                                    else
                                    {
                                        let reader = new FileReader();
                                        reader.readAsArrayBuffer(this.file);
                                        reader.onload = () =>
                                        {
                                            $.ajax({
                                                method: "POST",
                                                url: syt.backend + "/edit",
                                                xhrFields: {
                                                    withCredentials: true
                                                },
                                                contentType: false,
                                                processData: false,
                                                data: reader.result,
                                                dataType: "text",
                                                success: response =>
                                                {
                                                    switch (response)
                                                    {
                                                        case "ok":
                                                            window.location = "/photos";
                                                            break;
                                                        case "unauthorized":
                                                            error();
                                                            break;
                                                    }
                                                }
                                            });
                                        };
                                    }
                                    break;
                                case "unauthorized":
                                    error();
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }
</script>

<style scoped>
    form
    {
        background-color: aliceblue;
        border: solid 2px silver;
        padding: 2rem;
    }

    form>*
    {
        display: block;
        margin: 0.75rem;
    }

    [type="file"]~span
    {
        font-size: 10pt;
        margin-left: 1rem;
    }

    img
    {
        max-width: 100%;
        max-height: 80vh;
        margin: 1rem 0;
    }

    .info
    {
        margin-top: 1rem;
        display: grid;
        grid-template-columns: 20rem 1fr;
        grid-row-gap: 2rem;
    }

    label
    {
        display: contents;
    }

    label>span
    {
        font-weight: bold;
    }

    [type="text"]
    {
        width: unset;
    }

    textarea
    {
        resize: both;
        height: 100px;
    }

    .info>div:last-child
    {
        grid-column: 1/3;
    }

    [type="submit"]
    {
        grid-column: 2;
        justify-self: left;
        font-size: 14pt;
        border-radius: 5px;
        margin-top: 1.75rem;
        padding: 0.25rem 0.5rem 0.25rem 0.5rem;
    }
</style>