<script setup>
import Topbar from '@/Topbar.vue';
import utils from '@/utils';
import axios from 'axios';
import { ref } from 'vue';

const props=defineProps([
    "id"
])
const data=ref({})

axios.get(utils.host+"/get_item?id="+props.id).then(response=>{
    data.value=response.data
})
</script>

<template>
    <Topbar>
        Buyistic: 详情
    </Topbar>
    <div class="buypage_container" v-if="data!={}">
        <img class="buypage_img" :src="data.imgurl">
        <div class="buypage_content">
            <div class="buypage_title">
                {{ data.title }}
            </div>
            <div class="buypage_detail">
                {{ data.detail }}
            </div>
            <div class="buypage_prices">
                <div class="buypage_nowprice">
                    {{ data.nowprice }}
                </div>
                <div class="buypage_rawprice">
                    {{ data.rawprice }}
                </div>
            </div>
        </div>
    </div>
    <div class="buypage_wait" v-else>
        请等待网络相应。
    </div>
</template>