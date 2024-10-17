<script setup lang="ts">
import { ElButton } from 'element-plus';
import { useRouter } from 'vue-router';
const router=useRouter()
const props=defineProps([
    "imgurl",
    "title",
    "detail",
    "nowprice",
    "rawprice",
    "itemid",
    "type"
])

function jumpToBuyPage(){
    router.push("/buy?id="+props.itemid).catch(f=>{
        console.log(f)
    }).then(()=>{
        console.log(router.currentRoute)
        console.log(2)
    })
    console.log(11)
}
</script>

<template>
    <div class="shopping_item">
        <img class="shopping_img" :src="props.imgurl">
        <div class="shopping_content">
            <div class="shopping_text">
                <div class="shopping_title">
                    {{ props.title }}
                </div>
                <div class="shopping_detail">
                    {{ props.detail }}
                </div>
            </div>
            <div class="shopping_controller">
                <div class="shopping_price">
                    <div class="shopping_nowprice">
                        {{ props.nowprice }}
                    </div>
                    <div class="shopping_rawprice">
                        {{ props.rawprice }}
                    </div>
                </div>
                
                <div class="shopping_buttons">
                    <ElButton v-wave class="shopping_btn_buy" type="primary" @click="jumpToBuyPage">
                        购买
                    </ElButton> 
                </div>
            </div>
        </div>
    </div>
</template>

<style>
.shopping_item{
    display: flex;
    margin: 6px;
    padding: 6px;
    border-radius: 10px;
    border-width: 2px;
    border-color: #666;
    border-style: solid;
}
.shopping_img{
    padding: 6px;
}
.shopping_controller{
    display: flex;
}
.shopping_buttons{
    margin-left: auto;
    justify-self: flex-end;

}
.shopping_content{
    padding-left: 6px;
    width: 100%;
}
.shopping_title{
    font-weight: 600;
}
.shopping_detail{
    font-weight: 400;
    color:gray;
    font-size: small;
}
.shopping_price{
    display: flex;
    align-items: flex-end;
}
.shopping_nowprice{
    font-weight: 800;
    color:red;
}
.shopping_rawprice{
    font-weight: 400;
    color:gray;
    font-size: smaller;
    text-decoration: line-through;
    padding-left: 6px;
}
</style>