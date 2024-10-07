package com.amex.order.service;

import com.amex.order.dto.AmexOrderRequestDto;
import com.amex.order.dto.ItemRequestDto;
import com.amex.order.dto.ItemResponseDto;
import com.amex.order.entity.ItemEntity;
import com.amex.order.entity.OrderEntity;
import com.amex.order.exception.NotFoundException;
import com.amex.order.mapper.ItemDtoMapper;
import com.amex.order.mapper.ItemEntityMapper;
import com.amex.order.mapper.OrderDtoMapper;
import com.amex.order.model.AppleItem;
import com.amex.order.model.OrangeItem;
import com.amex.order.repository.ItemRepository;
import com.amex.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.amex.order.utils.OrderConstants.APPLE;
import static com.amex.order.utils.OrderConstants.ORANGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    ItemRequestDto appleRequest;
    ItemRequestDto orangeRequest;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ItemRepository itemRepository;

    @Spy
    private ItemDtoMapper itemDtoMapper = new ItemDtoMapper();

    @Spy
    private ItemEntityMapper itemEntityMapper = new ItemEntityMapper();

    @Spy
    private OrderDtoMapper orderDtoMapper = new OrderDtoMapper();

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        appleRequest = ItemRequestDto.builder().itemName(APPLE).quantity(10).build();
        orangeRequest = ItemRequestDto.builder().itemName(ORANGE).quantity(10).build();
    }

    @Test
    @DisplayName("When Name Item Not Found Then Exception")
    public void whenNameItemNotFound() {
        appleRequest.setItemName("apples");
        var apple = AppleItem.builder().build();
        apple.setQuantity(appleRequest.getQuantity());

        var request = new AmexOrderRequestDto();
        request.setItem(appleRequest);

        Exception exception = assertThrows(
                NotFoundException.class,
                () -> orderService.submit(request)
        );

        assertNotNull(exception);
        assertEquals("Item 'apples' not found",exception.getMessage());
    }

    @Test
    @DisplayName("When Valid Request Then Save Order")
    public void testSaveOrder() {
        var request = new AmexOrderRequestDto();
        request.setItem(appleRequest);
        request.setItem(orangeRequest);

        var apple = AppleItem.builder().build();
        apple.setQuantity(appleRequest.getQuantity());
        var appleResponse = ItemResponseDto.builder()
                .itemName(apple.name())
                .itemPrice(apple.price())
                .quantity(apple.quantity())
                .total(apple.total())
                .build();

        var orange = OrangeItem.builder().build();
        orange.setQuantity(orangeRequest.getQuantity());
        var orangeResponse = ItemResponseDto.builder()
                .itemName(orange.name())
                .itemPrice(orange.price())
                .quantity(orange.quantity())
                .total(orange.total())
                .build();

        var total = orange.total() + apple.total();
        var orderEntity = new OrderEntity(1L, total);

        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);
        when(itemRepository.saveAll(any()))
                .thenReturn(List.of(
                        new ItemEntity(1L, orderEntity.getId(), apple.name(), apple.price(), apple.quantity(), apple.total()),
                        new ItemEntity(1L, orderEntity.getId(), orange.name(), orange.price(), orange.quantity(), orange.total())
                ));

        var response = orderService.submit(request);

        assertEquals(total, response.getTotalOrder());
        assertThat(response.getItems()).hasSize(2);
        assertThat(response.getItems()).contains(appleResponse);
        assertThat(response.getItems()).contains(orangeResponse);
    }

    @Test
    @DisplayName("When Valid Order Id Then Return Order Summary")
    public void testReturnOrderSummary() {
        var apple = AppleItem.builder().build();
        apple.setQuantity(appleRequest.getQuantity());
        var appleResponse = ItemResponseDto.builder()
                .itemName(apple.name())
                .itemPrice(apple.price())
                .quantity(apple.quantity())
                .total(apple.total())
                .build();

        var orange = OrangeItem.builder().build();
        orange.setQuantity(orangeRequest.getQuantity());
        var orangeResponse = ItemResponseDto.builder()
                .itemName(orange.name())
                .itemPrice(orange.price())
                .quantity(orange.quantity())
                .total(orange.total())
                .build();

        var total = orange.total() + apple.total();
        var orderEntity = new OrderEntity(1L, total);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(orderEntity));
        when(itemRepository.findByOrderId(anyLong()))
                .thenReturn(Set.of(
                        new ItemEntity(1L, orderEntity.getId(), apple.name(), apple.price(), apple.quantity(), apple.total()),
                        new ItemEntity(2L, orderEntity.getId(), orange.name(), orange.price(), orange.quantity(), orange.total())
                ));

        var response = orderService.fetchOrderById(1L);

        assertEquals(1L, response.getId());
        assertThat(response.getItems()).hasSize(2);
        assertThat(response.getItems()).contains(appleResponse);
        assertThat(response.getItems()).contains(orangeResponse);
    }

    @Test
    @DisplayName("When Order Id Not Found Then Exception")
    public void whenOrderIdNotFoundThenException() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                NotFoundException.class,
                () -> orderService.fetchOrderById(1L)
        );

        assertEquals("Order '1' not found", exception.getMessage());
    }

    @Test
    @DisplayName("When Orders Then Return Orders Summary")
    public void whenOrdersThenReturnOrderSummary() {
        var apple = AppleItem.builder().build();
        apple.setQuantity(appleRequest.getQuantity());

        var orange = OrangeItem.builder().build();
        orange.setQuantity(orangeRequest.getQuantity());

        var total = orange.total() + apple.total();
        var orderEntity = new OrderEntity(1L, total);

        when(orderRepository.findAll()).thenReturn(List.of(orderEntity));
        when(itemRepository.findByOrderId(anyLong()))
                .thenReturn(Set.of(
                        new ItemEntity(1L, orderEntity.getId(), apple.name(), apple.price(), apple.quantity(), apple.total()),
                        new ItemEntity(1L, orderEntity.getId(), orange.name(), orange.price(), orange.quantity(), orange.total())
                ));

        var response = orderService.fetchAll();

        assertEquals(1, response.size());
    }

    @Test
    @DisplayName("When Empty Orders Then Exception")
    public void whenEmptyOrdersThenException() {
        when(orderRepository.findAll()).thenReturn(List.of());

        Exception exception = assertThrows(
                NotFoundException.class,
                () -> orderService.fetchAll()
        );

        assertEquals("No orders found", exception.getMessage());
    }

}