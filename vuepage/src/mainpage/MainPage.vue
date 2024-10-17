<script setup>
import axios from 'axios';
import ShoppingItem from './ShoppingItem.vue';
import utils from '../utils';
import { ref } from 'vue';
import Topbar from '@/Topbar.vue';

var items_data=ref([])
axios.get(utils.host + "/get_items").then(
    response => {
        items_data.value=response.data.data
    }
)
</script>

<template>
    <Topbar>
        Buyistic
    </Topbar>
    <div class="items_container">
        <ShoppingItem v-for="item in items_data"
            :imgurl="item.imgurl" :detail="item.detail" :title="item.title"
            :nowprice="item.nowprice" :rawprice="item.rawprice" :itemid="item.id" :type="item.type"/>
    </div>
</template>

<style>
</style>