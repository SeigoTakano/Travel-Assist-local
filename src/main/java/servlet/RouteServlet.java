package servlet;

import java.io.IOException;

import dao.RouteDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ルートの保存および削除を管理するサーブレット
 */
@WebServlet("/saveRoute")
public class RouteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 文字化け防止
        request.setCharacterEncoding("UTF-8");

        // 動作判別パラメータ（delete か それ以外か）
        String action = request.getParameter("action");
        RouteDao dao = new RouteDao();
        
        // テスト用ユーザーID（本来はセッション等から取得）
        int userId = 1; 
        boolean success = false;

        if ("delete".equals(action)) {
            // --- 削除処理 ---
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    int routeId = Integer.parseInt(idStr);
                    // RouteDaoに作成した削除メソッドを呼び出し
                    success = dao.deleteRoute(routeId);
                } catch (NumberFormatException e) {
                    System.err.println("【エラー】無効なID形式です: " + idStr);
                    success = false;
                }
            }
        } else {
            // --- 保存処理 (デフォルト) ---
            String start = request.getParameter("start");
            String end = request.getParameter("end");
            String polyline = request.getParameter("polyline");
            
            // "true" という文字列を boolean に変換
            boolean isFav = Boolean.parseBoolean(request.getParameter("isFavorite"));
            
            // RouteDaoの既存の保存メソッドを呼び出し
            success = dao.insertRoute(userId, start, end, polyline, isFav);
        }

        // 成功なら 200 (OK)、失敗なら 500 (サーバーエラー) を返却
        if (success) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}