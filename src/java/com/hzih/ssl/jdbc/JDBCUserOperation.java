package com.hzih.ssl.jdbc;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-28
 * Time: 下午1:22
 * To change this template use File | Settings | File Templates.
 */
public class JDBCUserOperation {
    //返回用户名的记录
    public  Map<String,Object> findUserByUserName(String username){
        JDBCUtil jdbcUtil = new JDBCUtil();
        List<Map<String,Object>> mapList = jdbcUtil.queryToList("select *from usermanage where cacn='"+username+"'");
        if(mapList.size()<=1){
            return mapList.get(0);
        }
        return null;
    }

    //返回用户名的记录
    public  String findIdByUser(String username){
        JDBCUtil jdbcUtil = new JDBCUtil();
        List<Map<String,Object>> mapList = jdbcUtil.queryToList("select id from usermanage where cacn='"+username+"'");
        if(mapList.size()>0){
            Object id = mapList.get(0).get("id");
            return id.toString();
        }
        return null;
    }
    
    public  String findRoleIdByUserId(String userId){
        JDBCUtil jdbcUtil = new JDBCUtil();
        List<Map<String,Object>> mapList = jdbcUtil.queryToList("select roleid from roleuser where userid='"+userId+"'");
        if(mapList.size()>0){
            Object id = mapList.get(0).get("roleid");
            return id.toString();
        }
        return null;
    }
    // 查找出roleName
    public  String findRoleNameByRoleId(String roleId){
        JDBCUtil jdbcUtil = new JDBCUtil();
        List<Map<String,Object>> mapList = jdbcUtil.queryToList("select rolename from rolemanage where id='"+roleId+"'");
        if(mapList.size()>0){
            Object id = mapList.get(0).get("rolename");
            return id.toString();
        }
        return null;
    }

    //查找权限
    public  List<Map<String,Object>> findPowersByRoleName(String roleName){
        JDBCUtil jdbcUtil = new JDBCUtil();
        List<Map<String,Object>> mapList = jdbcUtil.queryToList("select * from power where rolename='"+roleName+"' and rights=1");
        if(mapList.size()>0&&mapList!=null){
           return mapList;
        }
        return null;
    }
    
    //得到所有resource
    public  Map<String,Object> findResourceForPowerResourceName(String resourceName){
        JDBCUtil jdbcUtil = new JDBCUtil();
        List<Map<String,Object>> mapList = jdbcUtil.queryToList("select * from resource where name='"+resourceName+"'");
        if(mapList.size()>0&&mapList!=null){
            return mapList.get(0);
        }
        return null;
    }

    // 查找出resourcesWebOrIp的记录
    public  Map<String,Object> findResourceWebOrIpForResource(Map<String,Object> resource){
        if(resource!=null){
            if(resource.get("type").toString().equals("1")){
                JDBCUtil jdbcUtil = new JDBCUtil();
                List<Map<String,Object>> mapList = jdbcUtil.queryToList("select * from resourceweb where resourceid='"+resource.get("id").toString()+"'");
                if(mapList.size()>0&&mapList!=null){
                   return mapList.get(0);
                }
            }/*else {
                JDBCUtil jdbcUtil = new JDBCUtil();
                List<Map<String,Object>> mapList = jdbcUtil.queryToList("select * from resourceip where resourceid='"+resource.get("id").toString()+"'");
                if(mapList.size()>0&&mapList!=null){
                    return mapList.get(0);
                }
            }*/
        }
        return null;
    }

    //得到所有资源
    public  List<Map<String,Object>> getAllResourcesByUser(String username){
        List<Map<String,Object>> values = new ArrayList<>();
//      Map<String,Object> map = findUserByUserName("User");      //得到代表用户的整条记录
        String userId = findIdByUser(username);                    //得到用户id
        if(userId!=null){
            String roleId = findRoleIdByUserId(userId);               //用用户id查找出roleId
            if(roleId!=null){
                String roleName = findRoleNameByRoleId(roleId);      //用roleId查找roleName
                if(roleName!=null){
                    List<Map<String,Object>> powers = findPowersByRoleName(roleName);       //查找出所有权限
                    if(powers!=null){
                        for(Map<String,Object> map:powers){
                            Map<String,Object> resourceMap = findResourceForPowerResourceName(map.get("resourcename").toString());
                            if(resourceMap!=null){
                                Map<String,Object> r = findResourceWebOrIpForResource(resourceMap);
                                if(r!=null){
                                    values.add(r);
                                }
                            }
                        }
                        
                    }
                }
            }
        }
        if(values!=null){
          return values;
        }
        return null;
    }

    public   List<String> getAllUrls(List<Map<String,Object>> allResources){
        List<String> urls = new ArrayList<>();
         for(Map<String,Object> r:allResources){
             StringBuilder sb = new StringBuilder();
             sb.append(r.get("agreement").toString()).append("://");
             sb.append(r.get("ipaddress").toString()).append(":");
             sb.append(r.get("port").toString());
             String url = r.get("url").toString();
             if(!url.startsWith("/")){
                 sb.append("/");
             }
             sb.append(url);
             urls.add(sb.toString());
         }
        return urls;
    }
    
//    public static void main(String args[])throws Exception{
//          JDBCUserOperation jdbcUserOperation = new JDBCUserOperation();
//        List<Map<String,Object>> resources = jdbcUserOperation.getAllResourcesByUser("User");
//        List<String> urls = jdbcUserOperation.getAllUrls(resources);
//        for (String url:urls){
//            System.out.println(url);
//        }
//    }
}
