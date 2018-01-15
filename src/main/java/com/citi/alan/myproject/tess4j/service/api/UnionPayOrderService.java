package com.citi.alan.myproject.tess4j.service.api;

import java.io.File;
import java.util.Map;

public interface UnionPayOrderService {

     public Map<String, String> parseUnionPayOrder(File file);
}
