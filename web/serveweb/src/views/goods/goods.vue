<!--商品管理-->
<template>
    <div>
        <!--面包屑导航-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/dashboard' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/goods/goods' }">商品管理</el-breadcrumb-item>
            <el-breadcrumb-item>宠物商品</el-breadcrumb-item>
        </el-breadcrumb>


        <!--搜索筛选-->
        <div class="search">
            <el-input v-model="query.id" style="width: 200px" placeholder="输入商品id"></el-input>
            <el-input v-model="query.category2" style="width: 200px" placeholder="输入商品类型"></el-input>
            <el-input v-model="query.name" style="width: 200px" placeholder="输入商品名"></el-input>
            <el-button @click="getList()" type="primary" icon="el-icon-search">查询</el-button>
            <!--新增-->
            <el-button @click="handleCreate()" type="primary" icon="el-icon-search">新增</el-button>
        </div>

        <!--列表-->
        <el-table :data="tableData"
                  :header-cell-style="{background:'#eaeaea',color:'#555',height:'80px'}"
                  header-align="center"
                  stripe>
            <el-table-column prop="gid" label="商品id" width="80" align="center">
            </el-table-column>
            <el-table-column prop="image" label="商品图">
                <template slot-scope="scope">
                    <el-popover placement="top-start" title="" trigger="hover">
                        <img :src="scope.row.image" alt="" style="width: 150px;height: 150px;">
                        <img slot="reference" :src="scope.row.image" style="width: 50px;height: 50px">
                    </el-popover>
                </template>
            </el-table-column>

            <el-table-column prop="gname" label="商品名" width="150" align="center">
            </el-table-column>
            <el-table-column prop="category1" label="一级分类" width=150 align="center">
            </el-table-column>
            <el-table-column prop="category2" label="二级分类" width=150 align="center">
            </el-table-column>
            <el-table-column prop="gprice" label="商品单价" width="200" align="center">
            </el-table-column>
            <el-table-column prop="gdetail" label="商品描述" width="200" align="center">
            </el-table-column>
            <el-table-column prop="action" label="操作" align="center">
                <template slot-scope="scope">
                    <el-button
                            size="small"
                            @click="handleEdit(scope.row)">编辑
                    </el-button>
                    <el-button
                            size="small"
                            type="danger"
                            @click="handleDelete(scope)">删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>


        <!--分页组件-->
        <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="query.currentPage"
                :page-sizes="[5, 10, 20]"
                :page-size="this.query.pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="this.total">
        </el-pagination>


        <!--新增卡片-->
        <el-dialog class="goodscard" title="商品信息" :visible.sync="dialogFormVisible" :rules="dialogFormRules">
            <el-form :model="form">
                <el-form-item label="商品名称">
                    <el-input v-model="form.gname" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="商品图片">
                    <el-upload
                            class="avatar-uploader"
                            action="http://localhost:8902/oss/file/upload?module=image"
                            :show-file-list="false"
                            :on-success="handleAvatarSuccess"
                            accept=".jpg,.png">
                        <img v-if="form.image" :src="form.image" class="avatar"/>
                        <!--二选一-->
                        <!--<i v-else class="el-icon-plus avatar-uploader-icon"></i>-->
                        <el-button v-else class="icon" size="small" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                    </el-upload>
                </el-form-item>
                <el-form-item label="父级分类">
                    <!--级联选择器-->
                    <el-cascader
                            v-model="selectedKeys"
                            :options="catelist"
                            :props="cascaderProps"
                            @change="parentCateChanged()"
                            clearable>
                    </el-cascader>
                </el-form-item>
                <el-form-item label="商品单价">
                    <el-input v-model="form.gprice" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="商品描述">
                    <el-input v-model="form.gdetail" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="handleCancel">取 消</el-button>
                <el-button type="primary" @click="submitForm()">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
    // 直接树,扁平化的数据转换为树形数据
    function getChildrenCategories(categories, categoryId = 0) {
        const result = [];
        for (let i = 0; i < categories.length; i++) {
            const category = categories[i];
            if (category.catepid == categoryId) {
                const children = getChildrenCategories(categories, category.id);
                if (children.length) {
                    category.children = children;
                    result.push(category);
                } else {
                    category.children = undefined;
                    result.push(category);
                }
                // category.children = children;
                // result.push(category);
            }
        }
        return result;
    }


    export default {
        name: "goods",
        data() {
            return {
                tableData: [],
                total: 0,
                //来自分页和搜索的信息
                query: {
                    //当前页
                    currentPage: 1,
                    //每页数据条数
                    pageSize: 5,
                    id: '',
                    category2: '',
                    name: '',
                },
                dialogFormVisible: false,
                //卡片信息
                form: {
                    gname: '',
                    image: '',
                    category1: '',
                    category2: '',
                    gprice: '',
                    gdetail: ''
                },
                //指定级联选择器的配置对象
                cascaderProps: {
                    value: 'id',
                    label: 'catename',
                    children: 'children',
                    leaf: 'catelevel',
                    expandTrigger: 'hover', //悬浮显示
                    checkStrictly: true //可以选择任一级选项
                },
                //商品分类数据，默认为空
                catelist: [],
                //添加表单的验证规则对象
                dialogFormRules: {},
                //级联选择器选中的父级分类的ID
                selectedKeys: []
            }
        },
        //初始化调用
        mounted() {
            this.getList();
            this.getCategory();
        },
        methods: {
            //初始化
            getList() {
                this.axios.get('http://localhost:8902/goods/getlist', {
                    params: {
                        //将本页面的数据（this.query.xxx）传到后端GoodsReq和PageReq
                        currentPage: this.query.currentPage,
                        pageSize: this.query.pageSize,
                        Gid: this.query.id,
                        Category2: this.query.category2,
                        Gname: this.query.name
                    }
                }).then((resp) => {
                    console.log(resp)
                    this.tableData = resp.data.content.list;
                    this.total = resp.data.content.total;
                })
            },


            //删除
            handleDelete(scope) {
                let id = scope.row.gid;
                this.axios.delete("http://localhost:8902/goods/delete/" + id).then((resp) => {
                    let data = resp.data;
                    console.log(data)
                    if (data.success) {
                        this.getList();
                        this.$message({
                            message: '删除成功',
                            type: 'success',
                            center: true
                        });
                    }
                })
            },

            //分页
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
                this.query.pageSize = val;
                this.getList()
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.query.currentPage = val;
                this.getList()
            },
            //取消操作
            handleCancel() {
                this.dialogFormVisible = false;
                this.$message('取消操作');
            },
            //编辑修改
            handleEdit(row) {
                this.form = row;
                this.dialogFormVisible = true
            },

            //新增
            handleCreate() {
                this.form = {};
                this.dialogFormVisible = true
            },

            submitForm() {
                this.axios.post("http://localhost:8902/goods/save/", this.form).then((resp) => {
                    let data = resp.data;
                    if (data.success) {
                        this.dialogFormVisible = false;
                        //清空表单
                        this.form = {};
                        //查询提交的数据
                        this.getList();
                        this.$message({
                            message: '恭喜您，操作成功',
                            type: 'success',
                            center: true
                        })
                    }
                })
            },
            //上传成功后的处理
            handleAvatarSuccess(resp, file) {
                console.log(resp, "oss1");
                this.form.image = resp.content;
                // this.form.image = response.data.url;
                console.log(resp, "oss");
                console.log(this.form.image, "oss12312");
                //强制重新渲染
                this.$forceUpdate();
            },

            //获取商品分类数据
            getCategory() {
                this.axios.get('http://localhost:8902/cate/getall').then((resp) => {
                    console.log('resp.data', JSON.parse(JSON.stringify(resp.data)));
                    //扁平化的数据转变为多层数据
                    let data = resp.data;
                    this.catelist = getChildrenCategories(data);
                    console.log('this.catelist', this.catelist);
                });
            },
            parentCateChanged() {
                //如果 selectKeys数组的length大于0，证明选中了父级分类
                //如果 selectKeys的length小于0，证明没有选择父级分类
                console.log('this.selectedKeys', this.selectedKeys);
                if (this.selectedKeys.length === 0) {
                    return;
                }
                for (let i = 0; i < this.selectedKeys.length; i++) {
                    let parentId = this.selectedKeys[i];
                    try {
                        this.axios.get('http://localhost:8902/cate/getall').then((resp) => {
                            let data = resp.data;
                            let parentCategory = data.find(cate => cate.id === parentId);
                            console.log("parentcategory", parentCategory);
                            if (parentCategory) {
                                if (i === 0) {
                                    this.form.category1 = parentCategory.catename;
                                } else if (i === 1) {
                                    this.form.category2 = parentCategory.catename;
                                }
                                console.log("this.form", this.form);
                                // return;
                            }
                        });
                    } catch (error) {
                        console.error(error);
                    }
                }
            },
        }
    }
</script>

<style lang="scss" scoped>
    .el_breadbrum {
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
    }
    .search{
        margin: 10px;
        .el-input{
            width: 200px;
            margin-right: 20px
        }
    }

    .goodscard {
        /*width: 25%;*/
        display: inline-block;
        /*margin-right: 5%;*/
        justify-content: center;
        .el-form-item {
            display: flex;
            justify-content: center;
            /* 以下为可选的调整样式 */
            margin-bottom: 20px;
            /*width: 400px;*/
            .el-input, .el-cascader, .icon {
                width: 250px;
            }
        }
    }

    .el-upload {
        width: 250px;
    }

    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9 !important;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }

    .avatar-uploader .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 78px;
        line-height: 78px;
        text-align: center;
    }

    .avatar-uploader img {
        width: 178px;
        height: 178px;
        margin: 0 36px;
        display: block;
        object-fit: cover;
    }


</style>