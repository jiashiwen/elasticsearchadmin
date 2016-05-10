package com.leju.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leju.esrestful.service.CopyIndexService;
import com.leju.esrestful.service.ElasticsearchIndexUtilitiesService;

@RestController
@ComponentScan

public class CopyIndexController {
	@Autowired
	CopyIndexService copyindex;

	@Autowired
	ElasticsearchIndexUtilitiesService esutilt;

	@RequestMapping(value = "/copyindex")
	public String CopyIndex(@RequestParam(value = "source", required = true) String source,
			@RequestParam(value = "target", required = true) String target,
			@RequestParam(value = "type", defaultValue = "data") String type,
			@RequestParam(value = "overwrite", defaultValue = "rewrite") String overwrite) {
		/**
		 * @param source
		 *            源索引名称
		 * @param target
		 *            目标索引名称
		 * @param type
		 *            复制类型"metadata"或"data"。"metadata"表示复制索引元数据；"data"表示复制索引数据
		 * @param overwrite
		 *            覆盖方式"rewrite"或"force"。如果目标索引已存在，"rewrite"方式不更改目标索引结构，
		 *            直接取源索引数据写入目标索引；"force"方式删除目标索引并按源索引setting及mapping重建目标索引;
		 *            默认值为"rewrite" 若參數“type”为metadata，该参数值不能为"rewrite"。
		 */
		String result = "copy " + source + "  to " + target + " success!";

		if (overwrite.equals("force") && esutilt.IndexExistes(target)) {
			esutilt.DeleteIndex(target);
			copyindex.CopyIndexMetadata(source, target);
		}

		if (overwrite.equals("rewrite")) {
			if (type.equals("metadata")) {
				return "if param type is metadata, param overwrite must be force! ";
			}
		}

		if (type.equals("metadata") && !esutilt.IndexExistes(target) )  {
			if (copyindex.CopyIndexMetadata(source, target)) {
				return result;
			} else {
				result = "copy " + source + "  to " + target + " False!";
			}
		} else if (type.equals("data")) {
			if ( copyindex.CopyIndex(source, target)) {
				return result;
			} else {
				result = "copy " + source + "  to " + target + " False!";
			}
		}
		return result;

	}
}
