package b4u.pocketpartners.backend.operations.application.internal.queryservices;

import b4u.pocketpartners.backend.operations.domain.model.aggregates.Payment;
import b4u.pocketpartners.backend.operations.domain.model.queries.*;
import b4u.pocketpartners.backend.operations.domain.services.PaymentQueryService;
import b4u.pocketpartners.backend.operations.infrastructure.persistence.jpa.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentQueryServiceImpl implements PaymentQueryService {

    private final PaymentRepository paymentRepository;

    public PaymentQueryServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<Payment> handle(GetAllPaymentsQuery query){
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> handle(GetPaymentByIdQuery query){
        return paymentRepository.findById(query.paymentId());
    }

    @Override
        public List<Payment> handle(GetAllPaymentsByUserInformationIdQuery query){
        return paymentRepository.findAllByUserInformationId(query.userInformationId());
    }

    @Override
    public List<Payment> handle(GetAllPaymentsByExpenseIdQuery query){
        return paymentRepository.findAllByExpenseId(query.expenseId());
    }

    @Override
    public Optional<Payment> handle(GetPaymentByUserInformationIdAndExpenseId query){
        return paymentRepository.findByUserInformationIdAndExpenseId(query.userInformationId(), query.expenseId());
    }

    @Override
    public List<Payment> handle(GetAllPaymentsByUserIdAndStatusQuery query){
        return paymentRepository.findAllByUserInformationIdAndStatus(query.userInformationId(), query.status());
    }
}
