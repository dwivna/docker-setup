package com.dwivna.info.services;

import com.dwivna.info.models.Info;

public interface InfoService {

    Info saveInfo(Info info);

    Info getInfoByIdAndService(String id, String service);
}
