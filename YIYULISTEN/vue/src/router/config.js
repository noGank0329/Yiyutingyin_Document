import TabsView from '@/layouts/tabs/TabsView'
import BlankView from '@/layouts/BlankView'
import PageView from '@/layouts/PageView'

// 路由配置
const options = {
  routes: [
    {
      path: '/login',
      name: '登录页',
      component: () => import('@/pages/login')
    },
    {
      path: '/chat',
      name: '医生',
      component: () => import('@/pages/Im')
    },
    {
      path: '*',
      name: '404',
      component: () => import('@/pages/exception/404'),
    },
    {
      path: '/403',
      name: '403',
      component: () => import('@/pages/exception/403'),
    },
    {
      path: '/',
      name: '首页',
      component: TabsView,
      redirect: '/login',
      children: [

        // {
        //   path: 'dashboard',
        //   name: 'Dashboard',
        //   meta: {
        //     icon: 'dashboard'
        //   },
        //   component: BlankView,
        //   children: [
        //     {
        //       path: 'workplace',
        //       name: '工作台',
        //       meta: {
        //         page: {
        //           closable: false
        //         }
        //       },
        //       component: () => import('@/pages/dashboard/workplace'),
        //     },
        //     {
        //       path: 'analysis',
        //       name: '分析页',
        //       component: () => import('@/pages/dashboard/analysis'),
        //     }
        //   ]
        // },
        {
          path: 'personal',
          name: '个人中心',
          meta: {
            icon: 'user'
          },
          component: BlankView,
          children: [
            {
              path: 'personal',
              name: '个人主页',
              component: () => import('@/pages/personal/PersonalInformation')
            },
            {
              path: 'introduction',
              name: '团队介绍',
              meta: {
                invisible: true,
              },
              component: () => import('@/pages/personal/Introduction')
            },
            {
              path: 'news',
              name: '修改资讯',
              meta: {
                authority: {
                  role: 'manager'
                }
              },
              component: () => import('@/pages/personal/News')
            },
            {
              path: 'modifyinformation',
              name: '修改信息',
              meta: {
                authority: {
                  role: 'customer'
                }
              },
              component: () => import('@/pages/personal/ModifyInformation')
            },
          ],
        },

        {
          path: 'form',
          name: '问卷调查',
          meta: {
            icon: 'form',
            page: {
              cacheAble: false
            },
            authority: {
              role: 'customer'
            }
          },
          component: PageView,
          children: [
            {
              path: 'Paper',
              name: '问卷评估',
              component: () => import('@/pages/form/basic/Paper'),
            },
            {
              path: 'PaperHistory',
              name: '历史记录',
              component: () => import('@/pages/form/PaperHistory'),
            },
            // {
            //   path: 'step',
            //   name: '分步表单',
            //   component: () => import('@/pages/form/step'),
            // },
            // {
            //   path: 'advance',
            //   name: '高级表单',
            //   component: () => import('@/pages/form/advance'),
            // }
          ]
        },
        {
          path: 'list',
          name: '听力测试',
          meta: {
            icon: 'table',
            authority: {
              role: 'customer'
            }
          },
          component: PageView,
          children: [

            // {
            //   path: 'Music',
            //   name: '音乐',
            //   component: () => import('@/pages/Music')
            // },
            // {
            //   path: 'primary',
            //   name: '标准列表',
            //   component: () => import('@/pages/list/StandardList'),
            // },
            {
              path: 'test',
              name: '测试',
              component: () => import('@/pages/list/test'),
            },
            {
              path: 'TestHistory',
              name: '测试记录',
              // meta: {
              //   authority: 'queryForm',
              // },
              component: () => import('@/pages/list/TestHistory'),
            },
            {
              path: 'HistoryLine',
              name: '测试曲线图',
              meta: {
                invisible: true,

              },
              component: () => import('@/pages/list/HistoryLine'),
            },

            // {
            //   path: 'AllMusic',
            //   name: '音乐歌单',
            //   component: () => import('@/pages/list/AllMusic'),
            // },
            // {
            //   path: 'card',
            //   name: '卡片列表',
            //   component: () => import('@/pages/list/CardList'),
            // },
            // {
            //   path: 'search',
            //   name: '搜索列表',
            //   component: () => import('@/pages/list/search/SearchLayout'),
            //   children: [
            //     {
            //       path: 'article',
            //       name: '文章',
            //       component: () => import('@/pages/list/search/ArticleList'),
            //     },
            //     {
            //       path: 'application',
            //       name: '应用',
            //       component: () => import('@/pages/list/search/ApplicationList'),
            //     },
            //     {
            //       path: 'project',
            //       name: '项目',
            //       component: () => import('@/pages/list/search/ProjectList'),
            //     }
            //   ]
            // }
          ]
        },
        {
          path: 'music',
          name: '音乐',
          meta: {
            icon: 'appstore-o',
            authority: {
              role: ['customer', 'manager']
            }
          },
          component: PageView,
          children: [

            {
              path: 'Music',
              name: '音乐详情',
              meta: {
                invisible: true,


              },
              component: () => import('@/pages/Music')
            },
            // {
            //   path: 'primary',
            //   name: '标准列表',
            //   component: () => import('@/pages/list/StandardList'),
            // },

            {
              path: 'AllMusic',
              name: '音乐歌单',
              component: () => import('@/pages/list/AllMusic'),
            },
            //{
            //  path: 'caogao',
            //  name: '草稿',
            //  meta: {
            //    invisible: true
            //  },
            //  component: () => import('@/pages/list/caogao'),
            //},
            {
              path: 'Piano',
              name: '迷你钢琴',
              component: () => import('@/pages/Piano'),
            }


            // {
            //   path: 'card',
            //   name: '卡片列表',
            //   component: () => import('@/pages/list/CardList'),
            // },
            // {
            //   path: 'search',
            //   name: '搜索列表',
            //   component: () => import('@/pages/list/search/SearchLayout'),
            //   children: [
            //     {
            //       path: 'article',
            //       name: '文章',
            //       component: () => import('@/pages/list/search/ArticleList'),
            //     },
            //     {
            //       path: 'application',
            //       name: '应用',
            //       component: () => import('@/pages/list/search/ApplicationList'),
            //     },
            //     {
            //       path: 'project',
            //       name: '项目',
            //       component: () => import('@/pages/list/search/ProjectList'),
            //     }
            //   ]
            // }
          ]
        },
        {
          path: 'community',
          name: '社区论坛',
          meta: {
            icon: 'global',
            authority: {
              role: ['manager', 'customer']
            }
          },
          component: PageView,
          children: [
            {
              path: 'mainblog',
              name: '博客主页',
              meta: {
                authority: {
                  role: 'customer'
                }
              },
              component: () => import('@/pages/community/MainBlog')
            },
            {
              path: 'starblog',
              name: '我的收藏',
              meta: {
                authority: {
                  role: 'customer'
                }
              },
              component: () => import('@/pages/community/StarBlog')
            },
            {
              path: 'myblog',
              name: '我的博客',
              meta: {
                authority: {
                  role: 'customer'
                }
              },
              component: () => import('@/pages/community/MyBlog')
            },
            {
              path: 'postblog',
              name: '发布博客',
              meta: {
                authority: {
                  role: 'customer'
                }
              },
              component: () => import('@/pages/community/PostBlog')
            },
            {
              path: 'detailblog',
              name: '博客详情',
              meta: {
                invisible: true,
                authority: {
                  role: 'customer'
                }

              },
              component: () => import('@/pages/community/DetailBlog')
            },
            {
              path: 'checkblog',
              name: '博客审核',
              meta: {
                // invisible: true,
                authority: {
                  role: 'manager'
                }

              },
              component: () => import('@/pages/community/CheckBlog')
            }
          ]
        },
        {
          path: 'consult',
          name: '专业咨询',
          meta: {
            icon: 'contacts',
            authority: {
              role: ['customer', 'doctor']
            }
          },

          component: () => import('@/pages/consult/Consult')
        },
        {
          path: 'Chat',
          name: '医患交流',
          component: () => import('@/pages/Im'),
          meta: {
            icon: 'message',
            authority: {
              role: ['manager']
            }
          },
        },
        {
          path: 'feedback',
          name: '意见反馈',
          meta: {
            icon: 'form'
          },
          component: PageView,
          children: [
            {
              path: 'feedbackform',
              name: '提供反馈',
              meta: {
                authority: {
                  role: ['customer', 'doctor']
                }
              },
              component: () => import('@/pages/feedback/FeedbackForm')
            },
            {
              path: 'feedbackresult',
              name: '反馈记录',
              meta: {
                authority: {
                  role: ['customer', 'doctor']
                }
              },
              component: () => import('@/pages/feedback/FeedbackResult')
            },
            {
              path: 'feedbackcheck',
              name: '反馈回复',
              meta: {
                authority: {
                  role: 'manager'
                }
              },
              component: () => import('@/pages/feedback/FeedbackCheck')
            },
          ]
        },
        // {
        //   path: 'details',
        //   name: '详情页',
        //   meta: {
        //     icon: 'profile'
        //   },
        //   component: BlankView,
        //   children: [
        //     {
        //       path: 'basic',
        //       name: '基础详情页',
        //       component: () => import('@/pages/detail/BasicDetail')
        //     },
        //     {
        //       path: 'advance',
        //       name: '高级详情页',
        //       component: () => import('@/pages/detail/AdvancedDetail')
        //     }
        //   ]
        // },
        // {
        //   path: 'result',
        //   name: '结果页',
        //   meta: {
        //     icon: 'check-circle-o',
        //   },
        //   component: PageView,
        //   children: [
        //     {
        //       path: 'success',
        //       name: '成功',
        //       component: () => import('@/pages/result/Success')
        //     },
        //     {
        //       path: 'error',
        //       name: '失败',
        //       component: () => import('@/pages/result/Error')
        //     }
        //   ]
        // },
        // {
        //   path: 'exception',
        //   name: '异常页',
        //   meta: {
        //     icon: 'warning',
        //   },
        //   component: BlankView,
        //   children: [
        //     {
        //       path: '404',
        //       name: 'Exp404',
        //       component: () => import('@/pages/exception/404')
        //     },
        //     {
        //       path: '403',
        //       name: 'Exp403',
        //       component: () => import('@/pages/exception/403')
        //     },
        //     {
        //       path: '500',
        //       name: 'Exp500',
        //       component: () => import('@/pages/exception/500')
        //     }
        //   ]
        // },
        // {
        //   path: 'components',
        //   name: '内置组件',
        //   meta: {
        //     icon: 'appstore-o'
        //   },
        //   component: PageView,
        //   children: [
        //     {
        //       path: 'taskCard',
        //       name: '任务卡片',
        //       component: () => import('@/pages/components/TaskCard')
        //     },
        //     {
        //       path: 'palette',
        //       name: '颜色复选框',
        //       component: () => import('@/pages/components/Palette')
        //     },
        //     {
        //       path: 'table',
        //       name: '高级表格',
        //       component: () => import('@/pages/components/table')
        //     }
        //   ]
        // },
        // {
        //   name: '验权表单',
        //   path: 'auth/form',
        //   meta: {
        //     icon: 'file-excel',
        //     authority: {
        //       permission: 'form'
        //     }
        //   },
        //   component: () => import('@/pages/form/basic')
        // },
        // {
        //   name: '带参菜单',
        //   path: 'router/query',
        //   meta: {
        //     icon: 'project',
        //     query: {
        //       name: '菜单默认参数'
        //     }
        //   },
        //   component: () => import('@/pages/Demo')
        // },
        // {
        //   name: '动态路由菜单',
        //   path: 'router/dynamic/:id',
        //   meta: {
        //     icon: 'project',
        //     params: {
        //       id: 123
        //     }
        //   },
        //   component: () => import('@/pages/Demo')
        // },
        // {
        //   name: 'Ant Design Vue',
        //   path: 'antdv',
        //   meta: {
        //     icon: 'ant-design',
        //     link: 'https://www.antdv.com/docs/vue/introduce-cn/'
        //   }
        // },
        // {
        //   name: '使用文档',
        //   path: 'document',
        //   meta: {
        //     icon: 'file-word',
        //     link: 'https://iczer.gitee.io/vue-antd-admin-docs/'
        //   }
        // }
      ]
    },
  ]
}

export default options
