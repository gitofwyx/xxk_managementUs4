package com.xxk.core.util.tree;

import com.xxk.core.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamTreeUtil {

	/**
	 * 根据当前节点获得所有子节点id
	 * @param all 所有树数据
	 * @param BId 当前节点id
	 *            递归实现
	 */
	public static List<Map<String, Object>> getChildrenData(String BId, List<Map<String, Object>> all) {
		List<Map<String, Object>> children = all.stream().filter(MA ->
				MA.get("belong_to_id").equals(BId)
		).map(M -> {
			M.put("children",getChildrenData((String) M.get("value"), all));
			return M;
		}).collect(Collectors.toList());
		return children;
	}

}
