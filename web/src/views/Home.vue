<template>
  <a-layout>
    <a-layout-sider width="300" style="background: #fff">
      <a-menu
          mode="inline"
          :style="{ height: '100%', borderRight: 0 }"
          @click="handleClick"
      >
        <a-sub-menu v-for="item in level1" :key="item.id">
          <template v-slot:title>
            <span>{{item.name}}</span>
          </template>
          <a-menu-item v-for="child in item.children" :key="child.id">
            <span>{{child.name}}</span>
          </a-menu-item>
        </a-sub-menu>
      </a-menu>
    </a-layout-sider>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <div class="welcome" v-show="isShowWelcome">
        <h1>Welcome to my own wiki!</h1>
      </div>
      <a-list item-layout="vertical" size="large" :data-source="ebooks"
              :grid="{gutter: 20, column: 3}" v-show="!isShowWelcome">
        <template #renderItem="{ item }">
          <a-list-item key="item.name">
            <template #actions>
              <span v-for="{ type, text } in actions" :key="type">
                <component v-bind:is="type" style="margin-right: 8px"/>
                {{ text }}
              </span>
            </template>
            <a-list-item-meta :description="item.description">
              <template #title>
                <a :href="item.href">{{ item.name }}</a>
              </template>
              <template #avatar>
                <a-avatar :src="item.cover"/>
              </template>
            </a-list-item-meta>
          </a-list-item>
        </template>
      </a-list>
    </a-layout-content>
    </a-layout>
</template>


<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue'
import axios from 'axios'
import { message } from "ant-design-vue"
import { Tool } from "../../util/tool"

export default defineComponent({
  name: 'Home',
  setup() {
    const ebooks = ref();

    const level1 =  ref()
    let categorys: any

    const handleQueryCategory = () => {
      axios.get("/category/all").then((response) => {
        const data = response.data
        if (data.success) {
          categorys = data.content
          level1.value = []
          level1.value = Tool.array2Tree(categorys, 0)
        } else {
          message.error(data.message)
        }
      })
    }

    const isShowWelcome = ref(true)
    let categoryId2 = 0

    const handleQueryEbook = () => {
      axios.get("/ebook/list", {
        params: {
          page: 1,
          size: 1000,
          categoryId2: categoryId2
        }
      }).then((response) => {
        const data = response.data;
        ebooks.value = data.content.list;
      })
    }

    const handleClick = (value: any) => {
      isShowWelcome.value = false
      categoryId2 = value.key
      handleQueryEbook()
    }

    onMounted(() => {
      handleQueryCategory()
    })

    return {
      ebooks,

      actions: [
        {type: 'StarOutlined', text: '156'},
        {type: 'LikeOutlined', text: '156'},
        {type: 'MessageOutlined', text: '2'},
      ],

      handleClick,
      level1,

      isShowWelcome
    }
  }
})
</script>

<style scoped>
.ant-avatar {
  width: 50px;
  height: 50px;
  line-height: 50px;
  border-radius: 8%;
  margin: 5px 0;
}
</style>