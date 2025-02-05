<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <p>
        <a-form
            layout="inline"
            :model="param"
        >
          <a-form-item>
            <a-button type="primary" @click="add()">
              New
            </a-button>
          </a-form-item>
        </a-form>
      </p>
      <a-table
          :columns="columns"
          :row-key="record => record.id"
          :data-source="level1"
          :loading="loading"
          :pagination="false"
      >
        <template #cover="{ text: cover }">
          <img v-if="cover" :src="cover" alt="avatar"/>
        </template>
        <template v-slot:action="{ text, record }">
          <a-space size="small">
            <a-button type="primary" @click="edit(record)">
              Edit
            </a-button>
            <a-popconfirm
                title="Are you sure delete this item?"
                ok-text="Yes"
                cancel-text="No"
                @confirm="handleDelete(record.id)"
            >
              <a-button type="danger">
                Delete
              </a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </a-table>
    </a-layout-content>
  </a-layout>
  <a-modal
      title="Categories Table"
      v-model:visible="modalVisible"
      :confirm-loading="modalLoading"
      @ok="handleModalOk"
  >
    <a-form :model="category" :label-col="{ span:6 }" :wrapper-col="{ span: 18 }">
      <a-form-item label="name">
        <a-input v-model:value="category.name"/>
      </a-form-item>
      <a-form-item label="parent id">
        <a-select
            ref="select"
            v-model:value="category.parent"
        >
          <a-select-option value="0">None</a-select-option>
          <a-select-option v-for="c in level1" :key="c.id" :value="c.id" :disabled="category.id === c.id">{{c.name}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="order">
        <a-input v-model:value="category.sort" type="textarea"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue'
import axios from 'axios'
import { message } from "ant-design-vue"
import { Tool } from "../../../util/tool";

export default defineComponent({
  name: 'AdminCategory',
  setup() {
    const param = ref()
    param.value = {}
    const categorys = ref()
    const loading = ref(false)

    const columns = [
      {
        title: 'Name',
        dataIndex: 'name'
      },
      {
        key: 'action',
        slots: {customRender: 'action'}
      }
    ]

    const level1 = ref()
    level1.value = []

    const handleQuery = () => {
      loading.value = true
      level1.value = []
      axios.get("/category/all").then((response) => {
        loading.value = false
        const data = response.data
        if (data.success) {
          categorys.value = data.content
          level1.value = []
          level1.value = Tool.array2Tree(categorys.value, 0)
        } else {
          message.error(data.message)
        }
      })
    }

    const category = ref({})
    const modalVisible = ref(false)
    const modalLoading = ref(false)
    const handleModalOk = () => {
      modalLoading.value = true
      axios.post("/category/save", category.value).then((response) => {
        modalLoading.value = false
        const data = response.data
        if (data.success) {
          modalVisible.value = false
          handleQuery()
        } else {
          message.error(data.message)
        }
      })
    }

    const edit = (record: any) => {
      modalVisible.value = true
      category.value = Tool.copy(record)
    }

    const add = () => {
      modalVisible.value = true
      category.value = {}
    }

    const handleDelete = (id: number) => {
      axios.delete("/category/delete/" + id).then((response) => {
        const data = response.data
        if (data.success) {
          handleQuery()
        }
      })
    }

    onMounted(() => {
      handleQuery()
    })

    return {
      param,
      level1,
      columns,
      loading,
      edit,
      add,
      handleDelete,
      modalVisible,
      modalLoading,
      handleModalOk,
      handleQuery,
      category
    }
  }
})
</script>

<style scoped>
img {
  width: 50px;
  height: 50px;
}
</style>