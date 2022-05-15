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
            <a-input v-model:value="param.name" placeholder="Name"></a-input>
          </a-form-item>
          <a-form-item>
            <a-button
                type="primary"
                @click="handleQuery({page: 1, size: pagination.pageSize})"
            >
              Search
            </a-button>
          </a-form-item>
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
          :data-source="ebooks"
          :pagination="pagination"
          :loading="loading"
          @change="handleTableChange"
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
      title="Ebooks Table"
      v-model:visible="modalVisible"
      :confirm-loading="modalLoading"
      @ok="handleModalOk"
  >
    <a-form :model="ebook" :label-col="{ span:6 }" :wrapper-col="{ span: 18 }">
      <a-form-item label="cover">
        <a-input v-model:value="ebook.cover"/>
      </a-form-item>
      <a-form-item label="name">
        <a-input v-model:value="ebook.name"/>
      </a-form-item>
      <a-form-item label="category1">
        <a-input v-model:value="ebook.category1Id"/>
      </a-form-item>
      <a-form-item label="category2">
        <a-input v-model:value="ebook.category2Id"/>
      </a-form-item>
      <a-form-item label="description">
        <a-input v-model:value="ebook.description" type="textarea"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue'
import axios from 'axios'
import { message } from "ant-design-vue"
import {Tool} from "../../../util/tool";

export default defineComponent({
  name: 'AdminEbook',
  setup() {
    const param = ref()
    param.value = {}
    const ebooks = ref()
    const pagination = ref({
      current: 1,
      pageSize: 5,
      total: 0
    })
    const loading = ref(false)

    const columns = [
      {
        dataIndex: 'cover',
        slots: {customRender: 'cover'}
      },
      {
        title: 'Name',
        dataIndex: 'name'
      },
      {
        title: 'Category 1',
        key: 'category1Id',
        dataIndex: 'category1Id'
      },
      {
        title: 'Category 2',
        dataIndex: 'category2Id'
      },
      {
        title: 'Docs',
        dataIndex: 'docCount'
      },
      {
        title: 'Reads',
        dataIndex: 'viewCount'
      },
      {
        title: 'Votes',
        dataIndex: 'voteCount'
      },
      {
        key: 'action',
        slots: {customRender: 'action'}
      }
    ]

    const handleQuery = (params: any) => {
      loading.value = true
      axios.get("/ebook/list", {
        params: {
          page: params.page,
          size: params.size,
          name: param.value.name
        }
      }).then((response) => {
        loading.value = false
        const data = response.data
        if (data.success) {
          ebooks.value = data.content.list

          pagination.value.current = params.page
          pagination.value.total = data.content.total
        } else {
          message.error(data.message)
        }
      })
    }

    const handleTableChange = (pagination: any) => {
      handleQuery({
        page: pagination.current,
        size: pagination.pageSize
      })
    }

    const ebook = ref({})
    const modalVisible = ref(false)
    const modalLoading = ref(false)
    const handleModalOk = () => {
      modalLoading.value = true
      axios.post("/ebook/save", ebook.value).then((response) => {
        modalLoading.value = false
        const data = response.data
        if (data.success) {
          modalVisible.value = false
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize
          })
        } else {
          message.error(data.message)
        }
      })
    }

    const edit = (record: any) => {
      modalVisible.value = true
      ebook.value = Tool.copy(record)
    }

    const add = () => {
      modalVisible.value = true
      ebook.value = {}
    }

    const handleDelete = (id: number) => {
      axios.delete("/ebook/delete/" + id).then((response) => {
        const data = response.data
        if (data.success) {
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize
          })
        }
      })
    }

    onMounted(() => {
      handleQuery({
        page: 1,
        size: pagination.value.pageSize
      })
    })

    return {
      param,
      ebooks,
      pagination,
      columns,
      loading,
      handleTableChange,
      edit,
      add,
      handleDelete,
      modalVisible,
      modalLoading,
      handleModalOk,
      handleQuery,
      ebook
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